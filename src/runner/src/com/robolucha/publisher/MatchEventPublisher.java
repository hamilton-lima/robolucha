package com.robolucha.publisher;

import java.util.Iterator;

import org.apache.log4j.Logger;

import com.robolucha.game.event.MatchEventListener;
import com.robolucha.models.LuchadorMatchState;
import com.robolucha.models.Match;
import com.robolucha.models.MatchEvent;
import com.robolucha.models.MatchScore;
import com.robolucha.monitor.ThreadMonitor;
import com.robolucha.runner.MatchRunner;
import com.robolucha.runner.MatchRunnerAPI;
import com.robolucha.runner.luchador.LuchadorRunner;

public class MatchEventPublisher implements MatchEventListener {
	public static final String DAMAGE = "DAMAGE";
	public static final String KILL = "KILL";

	private Logger logger = Logger.getLogger(MatchEventPublisher.class);
	private Match match;

	public MatchEventPublisher(Match match) {
		this.match = match;
	}

	public void onInit(MatchRunner runner) {
		logger.debug("match INIT : " + runner.getThreadName());
	}

	public void onStart(MatchRunner runner) {
		logger.debug("match START : " + runner.getThreadName());
		long timestamp = System.currentTimeMillis();
		match.setTimeStart(timestamp);
		match.setLastTimeAlive(timestamp);

		try {
			MatchRunnerAPI.getInstance().updateMatch(match);
		} catch (Exception e) {
			reportErrors(runner, "ERROR, updating match:", e);
		}

	}

	public void onEnd(MatchRunner runner) {
		logger.debug("match END : " + runner.getThreadName());
		long timestamp = System.currentTimeMillis();
		match.setTimeEnd(timestamp);
		match.setLastTimeAlive(timestamp);

		try {
			MatchRunnerAPI.getInstance().updateMatch(match);
		} catch (Exception e) {
			reportErrors(runner, "ERROR, updating END of match:", e);
		}

		logger.info("Match END saving score");
		Iterator<Long> iterator = runner.getRunners().keySet().iterator();
		while (iterator.hasNext()) {
			Object key = (Object) iterator.next();
			LuchadorRunner luchadorRunner = runner.getRunners().get(key);
			updateScore(runner, luchadorRunner);
		}

	}

	public void onDamage(MatchRunner runner, LuchadorRunner luchadorA, LuchadorRunner luchadorB, Double amount) {

	}

	private void reportErrors(MatchRunner runner, String message, Exception e) {
		logger.error(message, e);
		ThreadMonitor.getInstance().addException(runner.getThreadName(), message);
	}

	private void updateScore(MatchRunner runner, LuchadorRunner luchadorRunner) {

		if (logger.isInfoEnabled()) {
			logger.info("update score=" + luchadorRunner.getScoreVO());
		}

		try {
			MatchScore score = MatchRunnerAPI.getInstance().findScore(runner.getMatch(),
					luchadorRunner.getGameComponent());

			if (score == null) {
				score = new MatchScore();
				score.setMatchRun(runner.getMatch());
				score.setGameComponent(luchadorRunner.getGameComponent());
				score.setKills(luchadorRunner.getScoreVO().getK());
				score.setDeaths(luchadorRunner.getScoreVO().getD());
				score.setScore(luchadorRunner.getScoreVO().getScore());
				MatchRunnerAPI.getInstance().addScore(score);
			} else {
				score.setKills(luchadorRunner.getScoreVO().getK());
				score.setDeaths(luchadorRunner.getScoreVO().getD());
				score.setScore(luchadorRunner.getScoreVO().getScore());

				MatchRunnerAPI.getInstance().updateScore(score);
			}
		} catch (Exception e) {
			reportErrors(runner, "ERROR, updating score:", e);

		}

	}

	public void onAlive(MatchRunner runner) {
		Match match = runner.getMatch();
		match.setLastTimeAlive(System.currentTimeMillis());

		try {
			MatchRunnerAPI.getInstance().updateMatch(match);
			ThreadMonitor.getInstance().alive(runner.getThreadName());
		} catch (Exception e) {
			reportErrors(runner, "ERROR, updating match last alive time:", e);
		}

	}

	@Override
	public void onKill(MatchRunner runner, LuchadorMatchState luchadorA, LuchadorMatchState luchadorB) {

		logger.debug("match onKill : " + runner.getThreadName());
		addLuchadorEvent(runner, luchadorA, luchadorB, 0.0, KILL);

	}

	@Override
	public void onDamage(MatchRunner runner, LuchadorMatchState luchadorA, LuchadorMatchState luchadorB,
			Double amount) {

		logger.debug("match onKill : " + runner.getThreadName());
		addLuchadorEvent(runner, luchadorA, luchadorB, amount, DAMAGE);

	}

	public void addLuchadorEvent(MatchRunner runner, LuchadorMatchState luchadorA, LuchadorMatchState luchadorB,
			Double amount, String name) {

		logger.debug("match onKill : " + runner.getThreadName());

		Match match = (Match) MatchRunnerAPI.getInstance().findMatchById(runner.getMatch().getId());

		MatchEvent event = new MatchEvent();
		event.setMatch(match);
		event.setComponentA(luchadorA.id);
		event.setComponentB(luchadorB.id);
		event.setAmount(amount);
		event.setTimeStart(System.currentTimeMillis());
		event.setEvent(name);

		if (logger.isDebugEnabled()) {
			logger.debug("matchrunner.id=" + runner.getMatch().getId() + " KILL event=" + event);
		}

		MatchRunnerAPI.getInstance().addMatchEvent(event);

		// TODO: publish the events to the PUB/SUB
		// updateScore(runner, luchadorA);
		// updateScore(runner, luchadorB);

		// reportErrors(runner, "ERROR, atualizando onKill :");

	}

}
