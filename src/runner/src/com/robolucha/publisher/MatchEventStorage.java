package com.robolucha.publisher;

import java.util.Iterator;

import org.apache.log4j.Logger;

import com.athanazio.saramago.server.dao.GenericDAO;
import com.athanazio.saramago.service.Response;
import com.robolucha.event.MatchEvent;
import com.robolucha.game.event.MatchEventListener;
import com.robolucha.models.Match;
import com.robolucha.models.MatchScore;
import com.robolucha.monitor.ThreadMonitor;
import com.robolucha.runner.LuchadorRunner;
import com.robolucha.runner.MatchRunner;
import com.robolucha.service.MatchRunCrudService;
import com.robolucha.service.MatchRunEventCrudService;

public class MatchEventStorage implements MatchEventListener {
	public static final String DAMAGE = "DAMAGE";
	public static final String KILL = "KILL";

	private Logger logger = Logger.getLogger(MatchEventStorage.class);
	private Match match;

	public MatchEventStorage(Match match) {
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
		Response response = MatchRunCrudService.getInstance().doSaramagoUpdate(match, new Response());

		reportErrors(runner, response, "ERROR, atualizando START MatchRun :");
	}

	
	public void onEnd(MatchRunner runner) {
		logger.debug("match END : " + runner.getThreadName());
		long timestamp = System.currentTimeMillis();
		match.setTimeEnd(timestamp);
		match.setLastTimeAlive(timestamp);
		
		Response response = MatchRunCrudService.getInstance().doSaramagoUpdate(match, new Response());

		logger.info("Match END salvando score");
		// percorre lista de lutchadores e salva o placar final da partida
		Iterator<Long> iterator = runner.getRunners().keySet().iterator();
		while (iterator.hasNext()) {
			Object key = (Object) iterator.next();
			LuchadorRunner luchadorRunner = runner.getRunners().get(key);
			updateScore(runner, luchadorRunner, response);
		}

		reportErrors(runner, response, "ERROR, atualizando END MatchRun :");

	}

	
	public void onDamage(MatchRunner runner, LuchadorRunner luchadorA, LuchadorRunner luchadorB, Double amount) {

		logger.debug("match onDamage : " + runner.getThreadName());
		Response response = new Response();

		Match match = (Match) MatchRunCrudService.getInstance().findById(runner.getMatch().getId());

		// cria evento
		MatchEvent event = new MatchEvent();
		event.setMatchRun(match);
		event.setAmount(amount);
		event.setLuchadorA(luchadorA.getGameComponent());
		event.setLuchadorB(luchadorB.getGameComponent());
		event.setTimeStart(System.currentTimeMillis());
		event.setEvent(DAMAGE);

		if (logger.isInfoEnabled()) {
			logger.info("matchrunner.id=" + runner.getMatch().getId() + " DAMAGE event=" + event.getEvent()
					+ " luchadorA=" + event.getLuchadorA().getId() + " luchadorB=" + event.getLuchadorB().getId()
					+ " amount=" + event.getAmount());
		}

		if (logger.isDebugEnabled()) {
			logger.debug("matchrunner.id=" + runner.getMatch().getId() + " DAMAGE event=" + event);
		}

		MatchRunEventCrudService.getInstance().doSaramagoAdd(event, response);

		updateScore(runner, luchadorA, response);
		updateScore(runner, luchadorB, response);

		reportErrors(runner, response, "ERROR, atualizando onDamage :");
	}

	
	public void onKill(MatchRunner runner, LuchadorRunner luchadorA, LuchadorRunner luchadorB) {

		logger.debug("match onKill : " + runner.getThreadName());
		Response response = new Response();

		Match match = (Match) MatchRunCrudService.getInstance().findById(runner.getMatch().getId());

		// cria evento
		MatchEvent event = new MatchEvent();
		event.setMatchRun(match);
		event.setLuchadorA(luchadorA.getGameComponent());
		event.setLuchadorB(luchadorB.getGameComponent());
		event.setAmount(0.0);
		event.setTimeStart((double) System.currentTimeMillis());
		event.setEvent(KILL);

		logger.info("matchrunner.id=" + runner.getMatch().getId() + " KILL event=" + event.getEvent() + " luchadorA="
				+ event.getLuchadorA().getId() + " luchadorB=" + event.getLuchadorB().getId());

		if (logger.isDebugEnabled()) {
			logger.debug("matchrunner.id=" + runner.getMatch().getId() + " KILL event=" + event);
		}

		MatchRunEventCrudService.getInstance().doSaramagoAdd(event, response);

		updateScore(runner, luchadorA, response);
		updateScore(runner, luchadorB, response);

		reportErrors(runner, response, "ERROR, atualizando onKill :");
	}

	private void reportErrors(MatchRunner runner, Response response, String message) {

		if (response.getErrors().size() > 0) {
			message += " " + response.getErrors();
			logger.error(message);

			ThreadMonitor.getInstance().addException(runner.getThreadName(), response.getErrors());
		}
	}

	private void updateScore(MatchRunner runner, LuchadorRunner luchadorRunner, Response response) {

		if (logger.isInfoEnabled()) {
			logger.info("update score=" + luchadorRunner.getScoreVO());
		}

		MatchScore score = (MatchScore) new MatchScore().clear();
		score.setMatchRun(runner.getMatch());
		score.setGameComponent(luchadorRunner.getGameComponent());

		score = (MatchScore) GenericDAO.getInstance().findExactOne(score);
		if (score == null) {
			score = new MatchScore();
			score.setMatchRun(runner.getMatch());
			score.setGameComponent(luchadorRunner.getGameComponent());
			score.setKills(luchadorRunner.getScoreVO().getK());
			score.setDeaths(luchadorRunner.getScoreVO().getD());
			score.setScore(luchadorRunner.getScoreVO().getScore());

			GenericDAO.getInstance().add(score);
		} else {
			score.setKills(luchadorRunner.getScoreVO().getK());
			score.setDeaths(luchadorRunner.getScoreVO().getD());
			score.setScore(luchadorRunner.getScoreVO().getScore());

			GenericDAO.getInstance().update(score);
		}
	}

	public void onAlive(MatchRunner runner) {
		Match match = runner.getMatch();
		match.setLastTimeAlive((double) System.currentTimeMillis());
		Response response = MatchRunCrudService.getInstance().doSaramagoUpdate(match, new Response());
		if (response.getErrors().size() > 0) {

			reportErrors(runner, response, "ERROR, atualizando alive da partida :");
		} else {
			ThreadMonitor.getInstance().alive(runner.getThreadName());
		}

	}

}
