package com.robolucha.publisher;

import java.util.LinkedList;
import java.util.Queue;

import org.apache.log4j.Logger;

import com.robolucha.game.event.MatchEventListener;
import com.robolucha.game.vo.EventVO;
import com.robolucha.runner.LuchadorRunner;
import com.robolucha.runner.MatchRunner;
import com.robolucha.runner.models.Match;

public class MatchEventToPublish implements MatchEventListener {
	public static final String DAMAGE = "DAMAGE";
	public static final String KILL = "KILL";

	private Logger logger = Logger.getLogger(MatchEventToPublish.class);
	private Match match;
	private Queue<EventVO> events;

	public MatchEventToPublish(Match match) {
		this.match = match;
		events = new LinkedList<EventVO>();
	}

	public void cleanup() {
		this.match = null;
		events.clear();
		events = null;
	}

	/**
	 * recupera evento do topo da lista para transmitir
	 * 
	 * @return
	 */
	public EventVO getEvent() {
		logger.debug("getEvent(), tamanho da lista de eventos : " + events.size());
		return events.poll();
	}

	public void onKill(MatchRunner runner, LuchadorRunner lutchadorA, LuchadorRunner lutchadorB) {

		logger.debug("match onKill : " + runner.getThreadName());
		EventVO event = new EventVO(lutchadorA.getGameComponent().getId(), lutchadorB.getGameComponent().getId(), KILL);
		logger.debug("-- evento adicionado : " + event);
		events.add(event);

		logger.debug("onKill(), tamanho da lista de eventos : " + events.size());

		// marca os pontos no lutchadorRunner
		ScoreBuilder.getInstance().addKillPoints(lutchadorA.getScoreVO());
		ScoreBuilder.getInstance().addKill(lutchadorA.getScoreVO());
		ScoreBuilder.getInstance().addDeath(lutchadorB.getScoreVO());

	}

	public void onDamage(MatchRunner runner, LuchadorRunner lutchadorA, LuchadorRunner lutchadorB, Double amount) {

		logger.debug("match onDamage : " + runner.getThreadName());

		// marca os pontos no lutchadorRunner
		ScoreBuilder.getInstance().addDamagePoints(lutchadorA.getScoreVO(), amount.intValue());
	}

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

}
