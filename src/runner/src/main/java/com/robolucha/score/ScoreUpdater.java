package com.robolucha.score;

import com.robolucha.game.event.MatchEventListener;
import com.robolucha.models.LuchadorMatchState;
import com.robolucha.runner.MatchRunner;
import org.apache.log4j.Logger;

public class ScoreUpdater implements MatchEventListener {
	public static final String DAMAGE = "DAMAGE";
	public static final String KILL = "KILL";

	private Logger logger = Logger.getLogger(ScoreUpdater.class);

	public ScoreUpdater() { }

	public void onInit(MatchRunner runner) {
		logger.debug("match INIT : " + runner.getThreadName());
	}

	public void onStart(MatchRunner runner) {
		logger.debug("match START : " + runner.getThreadName());
	}

	public void onEnd(MatchRunner runner) {
		logger.debug("match END : " + runner.getThreadName());
	}

	public void onAlive(MatchRunner runner) {
		logger.debug("match ALIVE : " + runner.getThreadName());
	}

	@Override
	public void onKill(MatchRunner runner, LuchadorMatchState lutchadorA, LuchadorMatchState lutchadorB) {

		logger.debug("match onKill : " + runner.getThreadName());

		// update luchadorRunner score
		ScoreBuilder.getInstance().addKillPoints(lutchadorA.getScore());
		ScoreBuilder.getInstance().addKill(lutchadorA.getScore());
		ScoreBuilder.getInstance().addDeath(lutchadorB.getScore());
	}

	@Override
	public void onDamage(MatchRunner runner, LuchadorMatchState lutchadorA, LuchadorMatchState lutchadorB,
			Double amount) {

		logger.debug("match onDamage : " + runner.getThreadName());

		// update luchadorRunner score
		ScoreBuilder.getInstance().addDamagePoints(lutchadorA.getScore(), amount.intValue());

	}

}
