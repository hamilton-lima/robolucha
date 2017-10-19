package com.robolucha.runner;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map.Entry;
import java.util.Queue;
import java.util.function.Function;

import javax.naming.Context;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import com.robolucha.event.GeneralEventHandler;
import com.robolucha.event.GeneralEventManager;
import com.robolucha.event.GeneralEventNames;
import com.robolucha.game.event.LuchadorEvent;
import com.robolucha.game.event.OnHitWallEvent;
import com.robolucha.game.vo.MaskConfigVO;
import com.robolucha.game.vo.MessageVO;
import com.robolucha.game.vo.ScoreVO;
import com.robolucha.models.Bullet;
import com.robolucha.models.Code;
import com.robolucha.models.GameComponent;
import com.robolucha.models.Luchador;
import com.robolucha.models.LuchadorMatchState;
import com.robolucha.models.MatchStateProvider;


/**
 * Representa a execucao de um lutchador em uma partida
 * 
 * @author hamiltonlima
 *
 */
public class LuchadorRunner implements GeneralEventHandler, MatchStateProvider {

	// TODO esta informacao precisa vir da configuracao do jogo
	public static final int DEFAULT_LIFE = 20;
	public static final int DEFAULT_PUNCH_AMOUNT = 2;
	public static final int DEFAULT_PUNCH_COOLDOWN = 2;
	private static final int MAX_COLOR_LENGTH = 7;

	public static final int DEFAULT_MOVE_SPEED = 50;
	public static final int DEFAULT_TURN_SPEED = 90;
	public static final int DEFAULT_TURNGUN_SPEED = 60;

	public static final double DEFAULT_RESPAWN_COOLDOWN = 10;

	public static final double DEFAULT_MAX_FIRE_COOLDOWN = 10;
	public static final double DEFAULT_MIN_FIRE_AMOUNT = 1;
	public static final double DEFAULT_MAX_FIRE_AMOUNT = 10;

	public static final double MAX_EVENTS_PER_TICK = 5;

	private static final String DEFAULT_METHODS_FILE = "default-methods.js";
	private static final String NMS_COLORS_FILE = "nmscolor.js";

	public static final String EVENT_ONHITWALL = "onHitWall";

	public static final String DEAD = "dead";

	public static final String COMMAND_FIRE = "fire";
	public static final String COMMAND_MOVE = "move";
	public static final String COMMAND_TURN = "turn";
	public static final String COMMAND_TURNGUN = "turnGun";

	public static final String ACTION_CHECK_RADAR = "checkRadar";
	private static final double DOUBLE_MIN_THRESHOLD = 0.01;

	private static Logger logger = Logger.getLogger(LuchadorRunner.class);

	// classes de test podem alterar este objeto
	GameComponent gameComponent;

	private ScoreVO score;
	private boolean active;
	private long start;
	private long elapsed;
	private String lastRunningError;

	private LinkedHashMap<String, LuchadorCommandAction> commands;
	LinkedHashMap<String, LuchadorEvent> events;
	private Queue<MessageVO> messages;

	private int size;
	private int halfSize;

	private Context cx;
	private Scriptable scope;
	private int exceptionCounter;

	// static {
	// Context.enter().setClassShutter(new RhinoWhiteList());
	// }

	private LuchadorMatchState state;

	private String currentJavascriptRunningName;
	private MatchRunner matchRunner;
	private double fireCoolDown;
	private double punchCoolDown;
	private double respawnCoolDown;
	ScriptRunner currentRunner;
	private MaskConfigVO mask;

	JavascriptFacade facade;

	public LuchadorRunner(GameComponent gameComponent, MatchRunner matchRunner, MaskConfigVO mask) {
		this.gameComponent = gameComponent;
		this.matchRunner = matchRunner;
		this.exceptionCounter = 0;
		this.mask = mask;

		this.size = matchRunner.getGameDefinition().getLuchadorSize();
		this.halfSize = this.size / 2;

		this.score = new ScoreVO(gameComponent.getId(), gameComponent.getName());
		this.active = false;
		this.commands = new LinkedHashMap<String, LuchadorCommandAction>();
		this.events = new LinkedHashMap<String, LuchadorEvent>();
		this.messages = new LinkedList<MessageVO>();

		try {
			setDefaultState(matchRunner.getRespawnPoint(this));
			createCodeEngine(gameComponent.getCodes());
			LuchadorCodeChangeListener.getInstance().register(this);
			this.active = true;
		} catch (Exception e) {
			logger.error("erro inicializando lutchador=" + gameComponent, e);
		}

		// registra para saber que nome de luchador mudou
		GeneralEventManager.getInstance().addHandler(GeneralEventNames.LUCHADOR_NAME_CHANGE, this);
	}

	// used for tests only
	void updateCodeEngine() throws Exception {
		createCodeEngine(gameComponent.getCodePackage().getCodes());
	}

	public void cleanup() {
		logger.info("*** luchador CLEAN-UP " + this.getGameComponent().getId());

		LuchadorCodeChangeListener.getInstance().remove(this);

		this.active = false;
		this.cx = null;
		this.scope = null;
		this.state = null;

		this.gameComponent = null;
		this.matchRunner = null;
		this.score = null;

		this.commands.clear();
		this.events.clear();
		this.messages.clear();

		this.commands = null;
		this.events = null;
		this.messages = null;

	}

	/**
	 * recebe evento de mudanca de nome de luchador
	 */
	@Override
	public void handle(String event, Object data) {

		if (logger.isDebugEnabled()) {
			logger.debug("handle de evento event=" + event);
			logger.debug("handle de evento data=" + data);
			logger.debug("gameComponent=" + this.gameComponent);
		}

		if (GeneralEventNames.LUCHADOR_NAME_CHANGE.equals(event)) {

			if (data != null && this.gameComponent != null) {
				Luchador changed = (Luchador) data;
				if (changed.getId() == this.gameComponent.getId()) {
					if (logger.isDebugEnabled()) {
						logger.debug("*** mesmo id, modificar ScoreVO");
					}
					this.score = new ScoreVO(this.score, changed.getName());
					this.gameComponent.setName(changed.getName());
				}
			}
		}

		if (logger.isDebugEnabled()) {
			logger.debug("this.score=" + this.score);
		}
	}

	public ScoreVO getScoreVO() {
		return this.score;
	}

	/**
	 * atualiza codigo com lutchador em execucao
	 * 
	 * @param luchador
	 */
	public void update(Luchador luchador) {
		logger.info("*** luchador UPDATE " + luchador.getId());

		try {
			this.gameComponent = luchador;
			this.messages.clear();
			updateCodeEngine(luchador.getCodePackage().getCodes());

		} catch (Exception e) {
			this.active = false;
			logger.warn("erro atualizando codigo, " + luchador);
			// TODO: responder feedback para usuario
		}
	}

	@Override
	public String toString() {
		String g = "null";
		if (gameComponent != null ) {
			g = Long.toString(gameComponent.getId());
		}

		return "LutchadorRunner [lutchador=" + g + ", active=" + active + ", start=" + start + ", elapsed=" + elapsed
				+ ", lastRunningError=" + lastRunningError + ", commands=" + commands + ", events=" + events
				+ ", size=" + size + ", halfSize=" + halfSize + ", cx=" + cx + ", scope=" + scope + ", state=" + state
				+ ", currentJavascriptRunningName=" + currentJavascriptRunningName + ", matchRunner.match.id="
				+ matchRunner.getMatch().getId() + ", fireCoolDown=" + fireCoolDown + "]";
	}

	private static String defaultMethodsCache;
	private static String nmsColorsCache;

	public static String getNmsColors() {
		if (nmsColorsCache == null) {
			nmsColorsCache = JavascriptUtil.getInstance().readDefinitions(LuchadorRunner.class, NMS_COLORS_FILE);
		}

		return nmsColorsCache;
	}

	public static String getDefaultMethods() {
		if (defaultMethodsCache == null) {
			defaultMethodsCache = JavascriptUtil.getInstance().readDefinitions(LuchadorRunner.class,
					DEFAULT_METHODS_FILE);
		}

		return defaultMethodsCache;
	}

	private void createCodeEngine(List<Code> codes) throws Exception {

		logger.debug("START createCodeEngine()");
		start = System.currentTimeMillis();
		try {
			cx = Context.enter();
			cx.setClassShutter(new RhinoWhiteList());

			scope = cx.initStandardObjects();

			addVariableToJS("me", this.state.getPublicState());
			addVariableToJS("ARENA_WIDTH", this.matchRunner.getGameDefinition().getArenaWidth());
			addVariableToJS("ARENA_HEIGHT", this.matchRunner.getGameDefinition().getArenaHeight());
			addVariableToJS("RADAR_ANGLE", this.getGameComponent().getRadarAngle());
			addVariableToJS("RADAR_RADIUS", this.getGameComponent().getRadarRadius());
			addVariableToJS("LUCHADOR_WIDTH", this.getSize());
			addVariableToJS("LUCHADOR_HEIGHT", this.getSize());

			this.facade = new JavascriptFacade(this);
			addVariableToJS("__internal", facade);
			String js = getDefaultMethods();
			eval(DEFAULT_METHODS_FILE, js);

			String nmsColors = getNmsColors();
			eval(NMS_COLORS_FILE, nmsColors);

			MethodBuilder.getInstance().buildAll(this, codes);
			updateInvalidCodes(codes);

		} catch (Exception e) {
			logger.error("erro executando inicializacao de codigo", e);
			throw e;
		} finally {
			Context.exit();
			Context cx2 = Context.getCurrentContext();
			logger.debug("cx2=" + cx2);
		}

		elapsed = System.currentTimeMillis() - start;
		logger.info("script executado em :" + elapsed + " ms.");
		logger.debug("END createCodeEngine()");
	}

	public void updateCodeEngine(List<Code> codes) throws Exception {

		logger.debug("START updateCodeEngine()");
		this.active = false;
		this.lastRunningError = null;

		try {
			createCodeEngine(codes);
			this.active = true;
		} catch (Exception e) {
			logger.error("erro executando inicializacao de codigo", e);
			throw e;
		}

		logger.debug("END createCodeEngine()");

	}

	private void setDefaultState(RespawnPoint point) {
		this.state = new LuchadorMatchState(this);
		this.state.setId(gameComponent.getId());

		this.state.setX(point.x);
		this.state.setY(point.y);
		this.state.setLife(DEFAULT_LIFE);
		this.state.setAngle(0);
		this.state.setGunAngle(0);
		this.state.setFireCoolDown(0);

		respawnCoolDown = 0.0;
		fireCoolDown = 0.0;
		punchCoolDown = 0.0;
	}

	void eval(String name, String js) {
		cx.evaluateString(scope, js, name, 0, null);
	}

	String getFromJavascript(String name) {
		String result = null;

		try {
			Context cx = Context.enter();
			cx.setClassShutter(new RhinoWhiteList());

			Object fromJs = cx.evaluateString(scope, name, "", 0, null);

			if (fromJs != null) {
				result = fromJs.toString();
			}
		} catch (Exception e) {
			logger.warn("Erro recuperando valor de : [" + name + "], " + e.getMessage());
		} finally {
			Context.exit();
		}

		return result;
	}

	private void addVariableToJS(String name, Object object) {
		Object wrappedOut = Context.javaToJS(object, scope);
		scope.put(name, scope, wrappedOut);
	}

	/**
	 * persiste no banco de dados casos de Codigo com erro para outros servicos
	 * mostrarem ao usuario final que determinado codigo esta com erro
	 * 
	 * @param codes
	 * @throws Exception
	 */
	private void updateInvalidCodes(List<Code> codes) throws Exception {

		if (codes == null) {
			logger.warn("lista de codigos vazia.");
			return;
		}

		Iterator iterator = codes.iterator();
		while (iterator.hasNext()) {
			Code code = (Code) iterator.next();
			if (code.getException() != null && code.getException().trim().length() > 0) {

				addMessage(MessageVO.DANGER, code.getEvent(), code.getException());

				Response response = CodeCrudService.getInstance().doSaramagoUpdate(code, new Response());

				// se houve falha ao atualizar Code
				if (response.getErrors().size() > 0) {
					logger.error("erro atualizando CODE no banco de dados : " + response.getErrors().toString());
					throw new Exception(response.getErrors().toString());
				}
			}

		}

	}

	private class ScriptRunner implements Runnable {

		private Object[] parameter;
		private String name;
		private LuchadorRunner luchadorRunner;

		private ScriptRunner(LuchadorRunner luchadorRunner, String name, Object... parameter) {
			this.name = name;
			this.parameter = parameter;
			this.luchadorRunner = luchadorRunner;
		}

		@Override
		public void run() {
			Thread.currentThread().setName(
					"ScriptRunner-Thread-GameComponentID-" + luchadorRunner.gameComponent.getId());

			if (logger.isDebugEnabled()) {
				logger.debug("START run(" + name + "," + parameter + ")");
			}

			Object result = null;
			// long elapsed = 0L;
			// long start = 0L;

			try {
				start = System.currentTimeMillis();
				currentJavascriptRunningName = name;
				Context cx = Context.enter();
				cx.setClassShutter(new RhinoWhiteList());

				Object fObj = scope.get(name, scope);

				if (!(fObj instanceof Function)) {
					if (logger.isDebugEnabled()) {
						logger.debug("function " + name + " is empty or not found");
					}
					// currentJavascriptRunningName = null;
					// luchadorRunner.exceptionCounter++;
					// throw new
					// NoSuchMethodException("server.exception.function.notfound="
					// + name);
				} else {
					Function f = (Function) fObj;

					if (logger.isDebugEnabled()) {
						logger.debug("== executando funcao " + name + "()");
					}

					result = f.call(cx, scope, null, parameter);
				}
			} catch (Exception e) {
				String message = "server.exception.error.running=run(" + name + "," + parameter + ") " + e.getMessage();
				if (logger.isDebugEnabled()) {
					logger.error(message, e);
				} else {
					logger.error(message);
				}

				luchadorRunner.exceptionCounter++;
				luchadorRunner.saveExceptionToCode(name, e.getMessage());

			} finally {
				try {
					Context.exit();
					// cx = null;
				} catch (IllegalStateException e) {
					if (logger.isEnabledFor(Level.WARN)) {
						logger.warn("desligando contexto javascript invalido executando : run(" + name + ","
								+ parameter + ")", e);
					}
				}
			}

			logger.debug("END run(), result=" + result);

			elapsed = System.currentTimeMillis() - start;
			currentJavascriptRunningName = null;

			// libera para a proxima execucao
			currentRunner = null;

			// libera referencia
			this.name = null;
			this.parameter = null;
			this.luchadorRunner = null;
		}

	}

	public void saveExceptionToCode(String name, String exception) {
		if (logger.isDebugEnabled()) {
			logger.debug("save exception to " + name + " exception=" + exception);
		}

		addMessage(MessageVO.DANGER, name, exception);

		for (Code code : getGameComponent().getCodePackage().getCodes()) {
			if (code.getEvent().equals(name)) {
				code.setException(exception);

				if (logger.isDebugEnabled()) {
					logger.debug("code atualizado " + code);
				}

				break;
			}
		}
	}

	/**
	 * executa o codigo javascript definido para o lutchador, executa em thread
	 * separado para garantir que um luchador nao para toda a execucao do
	 * servidor
	 * 
	 * @param name
	 * @param parameter
	 * @return
	 * @throws NoSuchMethodException
	 */
	public void run(String name, Object... parameter) throws Exception {

		// TODO: controlar o tempo de execucao para desativar o luchador
		if (this.currentRunner == null) {

			/**
			 * confere se existe exception gravada no codigo do luchador e se
			 * houver nao tenta executar novamente.
			 */
			boolean canRunCode = true;
			for (Code code : getGameComponent().getCodePackage().getCodes()) {
				if (code.getEvent().equals(name)) {
					if (code.getException() != null) {
						canRunCode = false;
						break;
					}
				}
			}
			
			// check if Runner is already running this piece of code
			if( commands.get(name) != null ){
				canRunCode = false;
			}

			if (canRunCode) {
				this.currentRunner = new ScriptRunner(this, name, parameter);
				Thread thread = new Thread(this.currentRunner);
				thread.start();
			}
		}
	}

	public void run(String name) throws Exception {
		Object[] empty = new Object[0];
		run(name, empty);
	}

	public GameComponent getGameComponent() {
		return gameComponent;
	}

	public boolean isActive() {
		return active;
	}

	public long getStart() {
		return start;
	}

	public long getElapsed() {
		return elapsed;
	}

	public LuchadorMatchState getState() {
		return state;
	}

	public void kill() {
		deactivate(DEAD);
		this.respawnCoolDown = DEFAULT_RESPAWN_COOLDOWN;
	}

	public double getRespawnCoolDown() {
		return respawnCoolDown;
	}

	public String getLastRunningError() {
		return lastRunningError;
	}

	public void deactivate(String reason) {
		logger.debug("deactivate=" + reason);

		this.active = false;
		this.lastRunningError = reason;
	}

	public void executeMove(double amount) {

		double addX = Math.cos(Calc.toRadian(state.getAngle())) * amount;
		double addY = Math.sin(Calc.toRadian(state.getAngle())) * amount;

		if (logger.isDebugEnabled()) {
			logger.debug("**** executeMove =" + amount);
			logger.debug(String.format("**** angle=%s addX=%s addY=%s", state.getAngle(), addX, addY));
		}

		if (Math.abs(addX) < DOUBLE_MIN_THRESHOLD) {
			addX = 0;
		}

		if (Math.abs(addY) < DOUBLE_MIN_THRESHOLD) {
			addY = 0;
		}

		if (logger.isDebugEnabled()) {
			logger.debug(String.format("depois de verificar DOUBLE_MIN_THRESHOLD, angle=%s addX=%s addY=%s",
					state.getAngle(), addX, addY));
		}

		double x = state.getX() + addX;
		double y = state.getY() + addY;

		if (logger.isDebugEnabled()) {
			logger.debug("move to x=" + x + ", y=" + y);
		}

		if (insideTheMapLimits(x, y) && matchRunner.canMove(this, x, y)) {
			this.state.setX(x);
			this.state.setY(y);
		}
	}

	/**
	 * verifica se lutchador esta nos limites do mapa e se tentar ultrapassar
	 * gera evento de hitWall
	 * 
	 * @param x
	 * @param y
	 * @return
	 */
	private boolean insideTheMapLimits(double x, double y) {

		if (!Calc.insideTheMapLimits(matchRunner.getGameDefinition(), x, y, halfSize)) {

			logger.debug("nao esta dentro dos limites do mapa : x=" + x + ",y=" + y);
			addEvent(new OnHitWallEvent(getState().getPublicState()));
			return false;
		}

		return true;
	}

	/**
	 * adiciona evento se nao existir na lista de eventos
	 * 
	 * @param event
	 *            .getKey()
	 */
	public void addEvent(LuchadorEvent event) {

		if (events.get(event.getKey()) == null) {
			events.put(event.getKey(), event);
		}
	}

	/**
	 * permite o processador de eventos usar o primeiro da fila e remover da
	 * fila
	 * 
	 * @return
	 */
	private LuchadorEvent getTopEvent() {

		Iterator<Entry<String, LuchadorEvent>> iterator = events.entrySet().iterator();
		if (iterator.hasNext()) {
			Entry<String, LuchadorEvent> next = iterator.next();
			iterator.remove();
			return next.getValue();
		}

		return null;
	}

	public void executeTurn(double amount) {
		double newAngle = this.getState().getAngle() + amount;
		newAngle = Calc.fixAngle(newAngle);
		this.getState().setAngle(newAngle);
	}

	public void executeTurnGun(double amount) {

		logger.debug(String.format("execute turn gun, amount : %s", amount));

		double newAngle = this.getState().getGunAngle() + amount;

		logger.debug(String.format("execute turn gun, new angle : %s", newAngle));
		newAngle = Calc.fixAngle(newAngle);
		logger.debug(String.format("execute turn gun, depois da validacao, new angle : %s", newAngle));

		this.getState().setGunAngle(newAngle);
	}

	/**
	 * executa o evento do topo da pilha de eventos solicita ao objeto do evento
	 * o nome do metodo javascript a ser executado e os parametros necessarios
	 * 
	 * @throws Exception
	 */
	public void triggerEvents() throws Exception {

		int counter = 0;
		LuchadorEvent event = getTopEvent();
		while (event != null && counter < MAX_EVENTS_PER_TICK) {
			matchRunner.addLuchadorEvent(event);
			run(event.getJavascriptMethod(), event.getMethodParameters());
			event = getTopEvent();
			counter++;
		}
	}

	/**
	 * executa o primeiro a ser encontrado com o nome de comando.
	 */
	public void consumeCommand() {
		logger.debug("consumeCommand()");

		if (commands.isEmpty()) {
			return;
		}

		Iterator<LuchadorCommandAction> iterator = commands.values().iterator();
		LuchadorCommandAction action = null;

		try {
			action = iterator.next();
		} catch (Exception e) {
			logger.warn("LuchadorCommandAction changed by other thread");
		}

		if( action == null ){
			return;
		}
		
		LuchadorCommand command = null;
		if (action.getCommands().size() > 0) {
			command = action.getCommands().getFirst();
		}

		// the action dont have any commands to execute, so remove from the
		// queue
		if (command == null) {
			iterator.remove();
		} else {

			if (logger.isDebugEnabled()) {
				logger.debug("*** command found=" + command);
				logger.debug("*** delta=" + matchRunner.getDelta());
				logger.debug("*** amount=" + command.getValue());
			}

			if (command.getCommand().equals(COMMAND_MOVE)) {

				double amount = command.consume(matchRunner.getDelta());

				if (logger.isDebugEnabled()) {
					logger.debug("move amount =" + amount);
				}

				executeMove(amount);

				if (command.empty()) {
					if (logger.isDebugEnabled()) {
						logger.debug("command empty");
					}
					action.getCommands().removeFirst();
				}
				return;
			}

			if (command.getCommand().equals(COMMAND_TURN)) {
				double amount = command.consume(matchRunner.getDelta());
				if (logger.isDebugEnabled()) {
					logger.debug("turn amount =" + amount);
				}

				executeTurn(amount);

				if (command.empty()) {
					if (logger.isDebugEnabled()) {
						logger.debug("command empty");
					}
					action.getCommands().removeFirst();
				}
				return;
			}

			if (command.getCommand().equals(COMMAND_TURNGUN)) {
				double amount = command.consume(matchRunner.getDelta());
				if (logger.isDebugEnabled()) {
					logger.debug("turnGUN amount =" + amount);
				}

				executeTurnGun(amount);

				if (command.empty()) {
					if (logger.isDebugEnabled()) {
						logger.debug("command empty");
					}
					action.getCommands().removeFirst();
				}
				return;
			}

			if (command.getCommand().equals(COMMAND_FIRE)) {

				// somente usa o disparo se o cooldown acabou
				if (fireCoolDown <= 0) {

					Bullet bullet = new Bullet(this, command.getOriginalValue(), state.getX(), state.getY(),
							state.getGunAngle());

					matchRunner.fire(bullet);

					// remove disparo da lista de comandos, nao precisa
					// consumir o comando o valor do amount eh aplicado
					// total de uma soh vez
					action.getCommands().removeFirst();

					// starts the timer for the fircooldown
					fireCoolDown = DEFAULT_MAX_FIRE_COOLDOWN * (bullet.getAmount() / DEFAULT_MAX_FIRE_AMOUNT);
				}
			}
		}

	}

	/**
	 * add a command to the commands queue only if: - if the action is not in
	 * queue - if action already exist in the queue AND the start miliseconds is
	 * the same, meaning that the command to insert is not the first line in the
	 * code, and the code has several commands to insert
	 * 
	 * example: running an onfound with: turnGun() + fire() will wait for this
	 * sequence to end until it can add a new onfound sequence.
	 * 
	 * @param command
	 */
	public void addCommand(LuchadorCommand command) {

		if (logger.isDebugEnabled()) {
			logger.debug(String.format("ADD Command( %s, %s)", command.getCommand(), command.getOriginalValue()));
			logger.debug(String.format("+++ command=%s", command));
		}

		// check if the action is already in the queue
		LuchadorCommandAction action = commands.get(this.currentJavascriptRunningName);
		if (logger.isDebugEnabled()) {
			logger.debug(String.format("+++ current command=%s", action));
		}

		if (action == null) {
			action = new LuchadorCommandAction(this.currentJavascriptRunningName, this.start);
			commands.put(this.currentJavascriptRunningName, action);
			action.getCommands().addLast(command);
		} else {
			// only add commands if the action is incomplete,
			// and the new command is in the same execution time (aka start in
			// milisecs)
			if (action.getStart() == this.start) {
				action.getCommands().add(command);
			}
		}

	}

	public void clearCommand(String prefix) {
		Iterator<String> iterator = commands.keySet().iterator();
		while (iterator.hasNext()) {
			String key = iterator.next();
			LuchadorCommandAction action = commands.get(key);
			action.clear(prefix);
		}
	}

	/**
	 * garante que valores de para disparo estejam dentro dos parametros de
	 * minimo e maximo
	 * 
	 * @param amount
	 * @return
	 */
	public int cleanUpAmount(int amount) {

		if (amount > DEFAULT_MAX_FIRE_AMOUNT) {
			amount = (int) DEFAULT_MAX_FIRE_AMOUNT;
		} else {
			if (amount <= DEFAULT_MIN_FIRE_AMOUNT) {
				amount = (int) DEFAULT_MIN_FIRE_AMOUNT;
			}
		}

		return amount;
	}

	public void updateFireCooldown() {
		if (this.fireCoolDown > 0) {
			this.fireCoolDown = this.fireCoolDown - matchRunner.getDelta();
		}

		this.state.setFireCoolDown(Calc.roundTo4Decimals(this.fireCoolDown));

		try {
			if (Context.getCurrentContext() == null) {
				cx = Context.enter();
				cx.setClassShutter(new RhinoWhiteList());
			}

			addVariableToJS("me", this.state.getPublicState());
		} catch (Exception e) {
			logger.error("erro atualizando public state depois de mudar firecooldown", e);

		} finally {
			Context.exit();
		}
	}

	public void updatePunchCooldown() {
		if (this.punchCoolDown > 0) {
			this.punchCoolDown = this.punchCoolDown - matchRunner.getDelta();
		}
	}

	public void updateRespawnCooldown() {
		if (this.respawnCoolDown > 0) {
			this.respawnCoolDown = this.respawnCoolDown - matchRunner.getDelta();
		}
	}

	public void punch() {
		if (punchCoolDown <= 0) {
			matchRunner.punch(this, DEFAULT_PUNCH_AMOUNT, state.getX(), state.getY(), state.getAngle());

			punchCoolDown = DEFAULT_PUNCH_COOLDOWN;
		}
	}

	public void clearAllCommands() {
		commands.clear();
	}

	public void say(String message) {
		matchRunner.listen(this, message);
	}

	public int getSize() {
		return size;
	}

	/**
	 * atualiza vida do lutchador
	 * 
	 * @param amount
	 */
	public void damage(double amount) {
		this.state.setLife(this.state.getLife() - amount);
	}

	public void respawn(RespawnPoint point) {
		setDefaultState(point);
		// garante que START sera executado no respawn
		MethodBuilder.getInstance().build(this, getStartCode());

		this.lastRunningError = "";
		this.active = true;
	}

	/**
	 * @todo check
	 * @return
	 */
	private Code getStartCode() {

		if (this.gameComponent.getCodePackage().getCodes() != null) {
			Iterator iterator = this.gameComponent.getCodePackage().getCodes().iterator();
			while (iterator.hasNext()) {
				Code code = (Code) iterator.next();
				if (code.getEvent().equals(MethodNames.START)) {
					return code;
				}
			}
		}
		return null;
	}

	public void addMessage(String type, String event, String message) {
		if (logger.isDebugEnabled()) {
			logger.debug(String.format("---- add message, type=%s, event=%s, message=%s", type, event, message));
		}

		messages.add(new MessageVO(type, event, message));
	}

	public void addMessage(String type, String message) {
		if (logger.isDebugEnabled()) {
			logger.debug(String.format("---- add message, type=%s, message=%s", type, message));
		}

		messages.add(new MessageVO(type, message));
	}

	public MessageVO getMessage() {
		MessageVO message = messages.poll();
		if (logger.isDebugEnabled()) {
			logger.debug(String.format("---- getMessage(), message=%s", message));
		}
		return message;
	}

	public void setHeadColor(String color) {
		if (color == null) {
			return;
		}

		if (color.length() > MAX_COLOR_LENGTH) {
			color = color.substring(0, MAX_COLOR_LENGTH);
		}

		this.state.setHeadColor(color);
	}

	public void setBodyColor(String color) {
		if (color == null) {
			return;
		}

		if (color.length() > MAX_COLOR_LENGTH) {
			color = color.substring(0, MAX_COLOR_LENGTH);
		}

		this.state.setBodyColor(color);
	}

	public void addFire(int amount) {
		amount = cleanUpAmount(amount);
		LuchadorCommand command = new LuchadorCommand(COMMAND_FIRE, amount, DEFAULT_MOVE_SPEED);
		addCommand(command);
	}

	public void addMove(int amount) {
		LuchadorCommand command = new LuchadorCommand(COMMAND_MOVE, amount, DEFAULT_MOVE_SPEED);
		addCommand(command);
	}

	public void addTurn(int amount) {
		LuchadorCommand command = new LuchadorCommand(COMMAND_TURN, amount, DEFAULT_TURN_SPEED);
		addCommand(command);
	}

	public void addTurnGun(int amount) {
		LuchadorCommand command = new LuchadorCommand(COMMAND_TURNGUN, amount, DEFAULT_TURNGUN_SPEED);
		addCommand(command);
	}

	public int getExceptionCounter() {
		return exceptionCounter;
	}

	public MaskConfigVO getMask() {
		return mask;
	}


	@Override
	public long getId() {
		return gameComponent.getId();
	}

}
