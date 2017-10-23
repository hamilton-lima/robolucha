package com.robolucha.runner.luchador;

import java.util.LinkedList;
import java.util.Queue;

import org.apache.log4j.Logger;

import com.athanazio.saramago.server.dao.GenericDAO;
import com.athanazio.saramago.server.util.JSONUtil;
import com.athanazio.saramago.service.Response;
import com.robolucha.game.vo.MaskConfigVO;
import com.robolucha.helper.BuildDefaultCustomerHelper;
import com.robolucha.models.GameComponent;
import com.robolucha.models.Luchador;
import com.robolucha.models.LuchadorMask;
import com.robolucha.models.MatchParticipant;
import com.robolucha.runner.MatchRunner;
import com.robolucha.service.MatchParticipantCrudService;

/**
 * 
 * @author Hamilton Lima
 *
 */
public class LutchadorRunnerCreator implements Runnable {

	static Logger logger = Logger.getLogger(LutchadorRunnerCreator.class);
	private static final long SLEEP = 5;

	private MatchRunner owner;
	private Queue<GameComponent> gameComponents;
	private Thread thread;
	private boolean alive;
	private String name;

	// private GameComponent gameComponent;

	public LutchadorRunnerCreator(MatchRunner owner) {
		this.owner = owner;
		this.alive = true;
		this.gameComponents = new LinkedList<GameComponent>();
		this.name = "LutchadorRunnerCreator-Thread-" + owner.getMatch().getId();

		thread = new Thread(this);
		thread.start();
	}

	public void cleanup() {
		this.alive = false;
	}

	public void add(GameComponent component) {
		logger.info("lutchador runner adicionado a fila para criacao : " + component);
		gameComponents.add(component);
	}

	public void run() {
		Thread.currentThread().setName(name);
		String message = name + " ainda vivo...";

		long logStart = System.currentTimeMillis();
		long logThreshold = 10000;

		while (alive) {

			GameComponent component = gameComponents.poll();
			if (component != null) {
				create(component);
			}

			if ((System.currentTimeMillis() - logStart) > logThreshold) {
				logStart = System.currentTimeMillis();
				logger.info(message);
			}

			try {
				Thread.sleep(SLEEP);
			} catch (InterruptedException e) {
				logger.error("interromperam este rapaz ocupado", e);
			}

		}

		gameComponents = null;

	}

	private void create(GameComponent gameComponent) {

		logger.info("lutchador runner iniciado (run): " + gameComponent);
		MaskConfigVO mask = null;

		if (gameComponent instanceof Luchador) {
			MatchParticipant matchParticipant = new MatchParticipant();
			matchParticipant.setTimeStart(System.currentTimeMillis());
			matchParticipant.setLuchador((Luchador) gameComponent);
			matchParticipant.setMatchRun(owner.getMatch());
			// TODO how to keep history??
			matchParticipant.getCodePackages().add(gameComponent.getCodes());

			if (matchParticipant.getMatchRun().getId() != null) {

				Response response = MatchParticipantCrudService.getInstance().doSaramagoAdd(matchParticipant,
						new Response());

				logger.info("----response=" + response);

				if (response.getErrors().size() > 0) {
					try {
						throw new Exception(JSONUtil.toJSON(response.getErrors()));
					} catch (Exception e) {
						logger.error("!!! erro gravando participante em partida", e);
					}
				} else {
					// foi tudo bem vamos recuperar a mascara do luchador
					mask = getMask(gameComponent);
				}
			} else {
				logger.warn("!!! tentando gravar participante com MatchRun nao salvo em banco, em TESTE ?");
			}

		} else {
			// gerar mascaras para nao luchadores
			mask = getMask(gameComponent);
		}

		LuchadorRunner runner = new LuchadorRunner(gameComponent, this.owner, mask);
		logger.info(">>>>>>>>> LUCHADOR runner criado : " + runner.getGameComponent().getId());

		if (logger.isDebugEnabled()) {
			logger.info(" runner=" + runner);
		}

		owner.runners.put(runner.getGameComponent().getId(), runner);
	}

	protected MaskConfigVO getMask(GameComponent gameComponent) {
		MaskConfigVO mask;
		LuchadorMask filter = new LuchadorMask();
		filter.setGameComponent(gameComponent);
		LuchadorMask found = (LuchadorMask) GenericDAO.getInstance().findOne(filter);
		if (found == null) {
			found = BuildDefaultCustomerHelper.addRandomMaskToGameComponent(gameComponent);
		}

		mask = maskVOBuild(found);
		return mask;
	}

	private MaskConfigVO maskVOBuild(LuchadorMask found) {
		MaskConfigVO result = new MaskConfigVO();
		result.background = found.getBackground();
		result.backgroundColor = found.getBackgroundColor();
		result.background2 = found.getBackground2();
		result.background2Color = found.getBackground2Color();
		result.ornamentTop = found.getOrnamentTop();
		result.ornamentTopColor = found.getOrnamentTopColor();
		result.ornamentBottom = found.getOrnamentBottom();
		result.ornamentBottomColor = found.getOrnamentBottomColor();
		result.face = found.getFace();
		result.faceColor = found.getFaceColor();
		result.mouth = found.getMouth();
		result.mouthColor = found.getMouthColor();
		result.eye = found.getEye();
		result.eyeColor = found.getEyeColor();

		return result;
	}

}