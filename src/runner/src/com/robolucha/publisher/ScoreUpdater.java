package com.robolucha.publisher;

import java.util.LinkedList;
import java.util.Queue;

import org.apache.log4j.Logger;

import com.robolucha.game.event.MatchEventListener;
import com.robolucha.game.vo.EventVO;
import com.robolucha.models.LuchadorMatchState;
import com.robolucha.models.Match;
import com.robolucha.runner.MatchRunner;

public class ScoreUpdater implements MatchEventListener {
	public static final String DAMAGE = "DAMAGE";
	public static final String KILL = "KILL";

	private Logger logger = Logger.getLogger(ScoreUpdater.class);
	private Match match;
	private Queue<EventVO> events;

	public ScoreUpdater(Match match) {
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
		EventVO event = new EventVO(lutchadorA.getId(), lutchadorB.getId(), KILL);
		logger.debug("-- evento added: " + event);
		events.add(event);

		logger.debug("onKill(), event list size : " + events.size());

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
