package com.robolucha.runner.luchador;

import java.util.LinkedList;
import java.util.Queue;

import org.apache.log4j.Logger;

import com.robolucha.models.GameComponent;
import com.robolucha.models.Luchador;
import com.robolucha.models.MaskConfig;
import com.robolucha.models.MaskConfigVO;
import com.robolucha.models.MatchParticipant;
import com.robolucha.runner.MatchRunner;
import com.robolucha.runner.MatchRunnerAPI;

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
		logger.info("gamecomponent added to creation queue: " + component);
		gameComponents.add(component);
	}

	public void run() {
		Thread.currentThread().setName(name);
		String message = name + " still alive...";

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
				logger.error("I sense an interruption in the force...", e);
			}

		}

		gameComponents = null;

	}

	private void create(GameComponent gameComponent) {

		logger.info("gamecomponent started (run): " + gameComponent);
		MaskConfigVO mask = null;

		if (gameComponent instanceof Luchador) {
			MatchParticipant matchParticipant = new MatchParticipant();
			matchParticipant.setTimeStart(System.currentTimeMillis());
			matchParticipant.setLuchador((Luchador) gameComponent);
			matchParticipant.setMatchRun(owner.getMatch());

			if (matchParticipant.getMatchRun().getId() != null) {

				MatchRunnerAPI.getInstance().addMatchParticipant(matchParticipant);

				mask = getMask(gameComponent);
			} else {
				logger.warn("!!! trying to save match participation with unsaved MatchRun, is it running a TEST?");
			}

		} else {
			// generate masks for gamecompoent that is not a luchador
			mask = getMask(gameComponent);
		}

		LuchadorRunner runner = new LuchadorRunner(gameComponent, this.owner, mask);
		logger.info(">>>>>>>>> LUCHADOR runner created: " + runner.getGameComponent().getId());

		if (logger.isDebugEnabled()) {
			logger.info(" runner=" + runner);
		}

		owner.getRunners().put(runner.getGameComponent().getId(), runner);
	}

	protected MaskConfigVO getMask(GameComponent gameComponent) {
		
		MaskConfig mask = MatchRunnerAPI.getInstance().findMask(gameComponent);

		if (mask == null) {
			mask = BuildDefaultLuchadorCoachHelper.addRandomMaskToGameComponent(gameComponent);
		}

		return mask;
	}

}