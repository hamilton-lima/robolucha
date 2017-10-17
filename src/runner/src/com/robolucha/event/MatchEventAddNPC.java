package com.robolucha.event;

import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;

import com.athanazio.saramago.server.dao.GenericDAO;
import com.robolucha.bean.GameDefinitionNPC;
import com.robolucha.game.event.MatchEventListener;
import com.robolucha.models.GameDefinition;
import com.robolucha.runner.LuchadorRunner;
import com.robolucha.runner.MatchRunner;

public class MatchEventAddNPC implements MatchEventListener {

	private Logger logger = Logger.getLogger(MatchEventAddNPC.class);

	/**
	 * procura por NPC relacionados a gamedefinition em questao
	 */
	@Override
	public void onInit(MatchRunner runner) {

		logger.info("START adicionar NPC a partida : " + runner.getThreadName());
		logger.info("Matchrunner = " + runner);

		GameDefinition def = runner.getGameDefinition();

		GameDefinitionNPC filter = (GameDefinitionNPC) new GameDefinitionNPC().clear();
		filter.setGameDefinition(def);

		List npcs = GenericDAO.getInstance().findAll(filter);
		Iterator iterator = npcs.iterator();
		while (iterator.hasNext()) {
			GameDefinitionNPC npc = (GameDefinitionNPC) iterator.next();
			for (int i = 0; i < npc.getCount(); i++) {
				runner.add(npc.getGameComponent());
				logger.info("adicionado lutchador a partida: " + npc.getGameComponent());
			}
		}

		logger.info("END adicionar NPC a partida : " + runner.getThreadName());
	}

	@Override
	public void onStart(MatchRunner runner) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onEnd(MatchRunner runner) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onKill(MatchRunner runner, LuchadorRunner lutchadorA,
			LuchadorRunner lutchadorB) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onDamage(MatchRunner runner, LuchadorRunner lutchadorA,
			LuchadorRunner lutchadorB, Double amount) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onAlive(MatchRunner matchRunner) {
		// TODO Auto-generated method stub
		
	}

}
