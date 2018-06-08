package com.robolucha.runner.luchador;

import com.robolucha.event.ConstEvents;
import com.robolucha.event.GeneralEventHandler;
import com.robolucha.event.GeneralEventManager;
import com.robolucha.game.event.LuchadorEvent;
import com.robolucha.game.event.OnHitWallEvent;
import com.robolucha.game.vo.MessageVO;
import com.robolucha.models.*;
import com.robolucha.runner.Calc;
import com.robolucha.runner.LuchadorCodeChangeListener;
import com.robolucha.runner.MatchRunner;
import com.robolucha.runner.RespawnPoint;
import com.robolucha.runner.luchador.lua.LuaFacade;
import org.apache.log4j.Logger;

import java.util.*;
import java.util.Map.Entry;

/**
 * Represents one play thread during the match
 *
 * @author hamiltonlima
 */
public class LuchadorRunner implements GeneralEventHandler, MatchStateProvider {

    public static final double MAX_EVENTS_PER_TICK = 5;

    public static final String EVENT_ONHITWALL = "onHitWall";

    public static final String DEAD = "dead";

    public static final String COMMAND_FIRE = "fire";
    public static final String COMMAND_MOVE = "move";
    public static final String COMMAND_TURN = "turn";
    public static final String COMMAND_TURNGUN = "turnGun";

    public static final String ACTION_CHECK_RADAR = "checkRadar";
    private static final double DOUBLE_MIN_THRESHOLD = 0.01;
    private static final int MAX_COLOR_LENGTH = 7;

    private static Logger logger = Logger.getLogger(LuchadorRunner.class);

    // test classes can update this.
    GameComponent gameComponent;

    private boolean active;
    private long start;
    private long elapsed;
    private String lastRunningError;

    private LinkedHashMap<String, LuchadorCommandAction> commands;
    LinkedHashMap<String, LuchadorEvent> events;
    private Queue<MessageVO> messages;

    private int size;
    private int halfSize;

    private ScriptDefinition scriptDefinition;
    int exceptionCounter;

    private LuchadorMatchState state;

    // TODO: remove this?
    private String getCurrentRunningCodeName() {
        if (this.currentRunner != null) {
            return this.currentRunner.getCurrentName();
        }
        return "";
    }

    private MatchRunner matchRunner;
    private double fireCoolDown;
    private double punchCoolDown;
    private double respawnCoolDown;
    ScriptRunner currentRunner;
    private MaskConfigVO mask;

    LuaFacade facade;

    public LuchadorRunner(GameComponent gameComponent, MatchRunner matchRunner, MaskConfigVO mask) {
        this.gameComponent = gameComponent;
        this.matchRunner = matchRunner;
        this.exceptionCounter = 0;
        this.mask = mask;

        this.size = matchRunner.getGameDefinition().getLuchadorSize();
        this.halfSize = this.size / 2;

        this.active = false;
        this.commands = new LinkedHashMap<String, LuchadorCommandAction>();
        this.events = new LinkedHashMap<String, LuchadorEvent>();
        this.messages = new LinkedList<MessageVO>();

        // TODO: add script type to the factory call to support multiple scripts
        scriptDefinition = ScriptDefinitionFactory.getInstance().getDefault();

        try {
            setDefaultState(matchRunner.getRespawnPoint(this));
            createCodeEngine(gameComponent.getCodes());

            // TODO: add this to GeneralEventManager?
            LuchadorCodeChangeListener.getInstance().register(this);
            this.active = true;
        } catch (Exception e) {
            logger.error("error on luchador constructor: " + gameComponent, e);
        }

        // listen to luchador name change
        GeneralEventManager.getInstance().addHandler(ConstEvents.LUCHADOR_NAME_CHANGE, this);
    }

    // used for tests only
    void updateCodeEngine() throws Exception {
        createCodeEngine(gameComponent.getCodes());
    }

    public void cleanup() {
        logger.info("*** luchador CLEAN-UP " + this.getGameComponent().getId());

        LuchadorCodeChangeListener.getInstance().remove(this);

        this.active = false;
        this.state = null;

        this.gameComponent = null;
        this.matchRunner = null;
        this.state.score = null;

        this.commands.clear();
        this.events.clear();
        this.messages.clear();

        this.commands = null;
        this.events = null;
        this.messages = null;

    }

    /**
     * handle name change events
     */
    @Override
    public void handle(String event, Object data) {

        if (logger.isDebugEnabled()) {
            logger.debug("handle de evento event=" + event);
            logger.debug("handle de evento data=" + data);
            logger.debug("gameComponent=" + this.gameComponent);
        }

        if (ConstEvents.LUCHADOR_NAME_CHANGE.equals(event)) {

            if (data != null && this.gameComponent != null) {
                Luchador changed = (Luchador) data;
                if (changed.getId() == this.gameComponent.getId()) {
                    if (logger.isDebugEnabled()) {
                        logger.debug("*** same id, change ScoreVO");
                    }
                    this.state.setName(changed.getName());
                    this.gameComponent.setName(changed.getName());
                }
            }
        }

        if (logger.isDebugEnabled()) {
            logger.debug("this.score=" + this.state.score);
        }
    }

    public ScoreVO getScoreVO() {
        return this.state.score;
    }

    /**
     * updates luchador code change on the fly
     *
     * @param luchador
     */
    public void update(Luchador luchador) {
        logger.info("*** luchador UPDATE " + luchador.getId());

        try {
            this.gameComponent = luchador;
            this.messages.clear();
            updateCodeEngine(luchador.getCodes());

        } catch (Exception e) {
            this.active = false;
            logger.warn("error updating code: " + luchador);
            // TODO: send feedback to the user
        }
    }

    private void updateScriptState(LuchadorPublicState state) throws Exception {
        scriptDefinition.set("me", state);
    }

    private void createCodeEngine(List<Code> codes) throws Exception {

        logger.debug("START createCodeEngine()");
        start = System.currentTimeMillis();
        try {

            // TODO: REMOVE SCORE FROM STATE
            updateScriptState(state.getPublicState());
            scriptDefinition.set("ARENA_WIDTH", this.matchRunner.getGameDefinition().getArenaWidth());
            scriptDefinition.set("ARENA_HEIGHT", this.matchRunner.getGameDefinition().getArenaHeight());
            scriptDefinition.set("RADAR_ANGLE", this.getGameComponent().getRadarAngle());
            scriptDefinition.set("RADAR_RADIUS", this.getGameComponent().getRadarRadius());
            scriptDefinition.set("LUCHADOR_WIDTH", this.getSize());
            scriptDefinition.set("LUCHADOR_HEIGHT", this.getSize());

            scriptDefinition.addFacade(scriptDefinition.buildFacade(this));
            scriptDefinition.loadDefaultLibraries();

            MethodBuilder.getInstance().buildAll(this, codes);
            updateInvalidCodes(codes);

        } catch (Exception e) {
            logger.error("error running code initialization", e);
            throw e;
        } finally {
            scriptDefinition.afterCompile();
        }

        elapsed = System.currentTimeMillis() - start;
        logger.info("script run in " + elapsed + " ms.");
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
            logger.error("error running code initialization", e);
            throw e;
        }

        logger.debug("END createCodeEngine()");

    }

    private void setDefaultState(RespawnPoint point) {
        this.state = new LuchadorMatchState();
        this.state.setId(gameComponent.getId());

        this.state.setX(point.x);
        this.state.setY(point.y);
        this.state.setLife(gameComponent.getLife());
        this.state.setAngle(0);
        this.state.setGunAngle(0);
        this.state.setFireCoolDown(0);

        this.state.score = new ScoreVO(gameComponent.getId(), gameComponent.getName());

        respawnCoolDown = 0.0;
        fireCoolDown = 0.0;
        punchCoolDown = 0.0;
    }

    void eval(String name, String script) throws Exception {
        scriptDefinition.eval(script);
    }

    String getString(String script) throws Exception {
        return scriptDefinition.getString(script);
    }

    private void updateInvalidCodes(List<Code> codes) throws Exception {

        if (codes == null) {
            logger.warn("list of codes is empty.");
            return;
        }

        Iterator iterator = codes.iterator();
        while (iterator.hasNext()) {
            Code code = (Code) iterator.next();
            if (code.getException() != null && code.getException().trim().length() > 0) {

                onMessage(MessageVO.DANGER, code.getEvent(), code.getException());

                // TODO: add call to save the code errors< or trigger events?
                /*
                 * Response response = CodeCrudService.getInstance().doSaramagoUpdate(code, new
                 * Response());
                 *
                 * // se houve falha ao atualizar Code if (response.getErrors().size() > 0) {
                 * logger.error("erro atualizando CODE no banco de dados : " +
                 * response.getErrors().toString()); throw new
                 * Exception(response.getErrors().toString()); }
                 */
            }

        }

    }

    public void saveExceptionToCode(String name, String exception) {
        if (logger.isDebugEnabled()) {
            logger.debug("save exception to " + name + " exception=" + exception);
        }

        onMessage(MessageVO.DANGER, name, exception);

        for (Code code : getGameComponent().getCodes()) {
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
     * Run the code in a separated thread
     *
     * @param name
     * @param parameter
     * @return
     * @throws NoSuchMethodException
     */
    public void run(String name, Object... parameter) throws Exception {

        // TODO: control running time to deactivate luchador
        if (this.currentRunner == null) {

            // check if the code has exception to avoid running defective code
            boolean canRunCode = true;
            for (Code code : getGameComponent().getCodes()) {
                if (code.getEvent().equals(name)) {
                    if (code.getException() != null) {
                        canRunCode = false;
                        break;
                    }
                }
            }

            // check if Runner is already running this piece of code
            if (commands.get(name) != null) {
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
        this.respawnCoolDown = gameComponent.getRespawnCooldown();
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
            logger.debug(String.format("after check DOUBLE_MIN_THRESHOLD, angle=%s addX=%s addY=%s", state.getAngle(),
                    addX, addY));
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
     * check if luchador is inside the map limits, trigger onHitWall if necessary
     *
     * @param x
     * @param y
     * @return
     */
    private boolean insideTheMapLimits(double x, double y) {

        if (!Calc.insideTheMapLimits(matchRunner.getGameDefinition(), x, y, halfSize)) {

            if (logger.isDebugEnabled()) {
                logger.debug("not inside the map limits: x=" + x + ",y=" + y);
            }

            addEvent(new OnHitWallEvent(getState().getPublicState()));
            return false;
        }

        return true;
    }

    /**
     * add event if is not in the event list
     *
     * @param event .getKey()
     */
    public void addEvent(LuchadorEvent event) {

        if (events.get(event.getKey()) == null) {
            events.put(event.getKey(), event);
        }
    }

    /**
     * Allow the event processor to get the first in the queue of events
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
        logger.debug(String.format("execute turn gun, after validation, new angle : %s", newAngle));

        this.getState().setGunAngle(newAngle);
    }

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

        if (action == null) {
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

                // only if the fire cooldown is over
                if (fireCoolDown <= 0) {

                    Bullet bullet = new Bullet(matchRunner.getGameDefinition(), this, command.getOriginalValue(),
                            state.getX(), state.getY(), state.getGunAngle());

                    matchRunner.fire(bullet);

                    // remove the fire from the command list, dont need to consume
                    // the command, the amount is aplied all at once
                    action.getCommands().removeFirst();

                    // starts the timer for the firecooldown
                    fireCoolDown = gameComponent.getMaxFireCooldown()
                            * (bullet.getAmount() / gameComponent.getMaxFireAmount());
                }
            }
        }

    }

    /**
     * add a command to the commands queue only if: - if the action is not in queue
     * - if action already exist in the queue AND the start miliseconds is the same,
     * meaning that the command to insert is not the first line in the code, and the
     * code has several commands to insert
     * <p>
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
        LuchadorCommandAction action = commands.get(command.getCommand());
        if (logger.isDebugEnabled()) {
            logger.debug(String.format("+++ current command=%s", action));
        }

        if (action == null) {
            action = new LuchadorCommandAction(command.getCommand(), this.start);
            commands.put(command.getCommand(), action);
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
     * keep the fire values in the correct range
     *
     * @param amount
     * @return
     */
    public int cleanUpAmount(int amount) {

        if (amount > gameComponent.getMaxFireAmount()) {
            amount = (int) gameComponent.getMaxFireAmount();
        } else {
            if (amount <= gameComponent.getMinFireAmount()) {
                amount = (int) gameComponent.getMinFireAmount();
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
            updateScriptState(state.getPublicState());
        } catch (Exception e) {
            logger.error("error update public state after changing firecooldown", e);

        } finally {
            scriptDefinition.afterCompile();
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
            matchRunner.punch(this, gameComponent.getPunchDamage(), state.getX(), state.getY(), state.getAngle());

            punchCoolDown = gameComponent.getPunchCoolDown();
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

    public void damage(double amount) {
        this.state.setLife(this.state.getLife() - amount);
    }

    public void respawn(RespawnPoint point) {
        setDefaultState(point);

        // make sure start is runned when respawn
        MethodBuilder.getInstance().build(this, getStartCode());

        this.lastRunningError = "";
        this.active = true;
    }

    /**
     * @return
     * @todo check
     */
    private Code getStartCode() {

        if (this.gameComponent.getCodes() != null) {
            Iterator iterator = this.gameComponent.getCodes().iterator();
            while (iterator.hasNext()) {
                Code code = (Code) iterator.next();
                if (code.getEvent().equals(MethodNames.START)) {
                    return code;
                }
            }
        }
        return null;
    }

    public void onMessage(String type, String event, String message) {
        if (logger.isDebugEnabled()) {
            logger.debug(String.format("---- add message, type=%s, event=%s, message=%s", type, event, message));
        }

        matchRunner.getOnMessage().onNext(new MessageVO(type, event, message));
    }

    public void onMessage(String type, String message) {
        if (logger.isDebugEnabled()) {
            logger.debug(String.format("---- add message, type=%s, message=%s", type, message));
        }

        matchRunner.getOnMessage().onNext(new MessageVO(type, message));
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
        LuchadorCommand command = new LuchadorCommand(COMMAND_FIRE, amount, gameComponent.getMoveSpeed());
        addCommand(command);
    }

    public void addMove(int amount) {
        LuchadorCommand command = new LuchadorCommand(COMMAND_MOVE, amount, gameComponent.getMoveSpeed());
        addCommand(command);
    }

    public void addTurn(int amount) {
        LuchadorCommand command = new LuchadorCommand(COMMAND_TURN, amount, gameComponent.getTurnSpeed());
        addCommand(command);
    }

    public void addTurnGun(int amount) {
        LuchadorCommand command = new LuchadorCommand(COMMAND_TURNGUN, amount, gameComponent.getTurnGunSpeed());
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

    public ScriptDefinition getScriptDefinition() {
        return scriptDefinition;
    }

    public void setScriptDefinition(ScriptDefinition scriptDefinition) {
        this.scriptDefinition = scriptDefinition;
    }

    public ScriptRunner getCurrentRunner() {
        return currentRunner;
    }
}
