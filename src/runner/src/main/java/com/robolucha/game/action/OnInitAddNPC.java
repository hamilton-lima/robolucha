package com.robolucha.game.action;

import java.util.Iterator;

import org.apache.log4j.Logger;

import com.robolucha.game.event.MatchEventListener;
import com.robolucha.models.GameComponent;
import com.robolucha.models.GameDefinition;
import com.robolucha.models.LuchadorMatchState;
import com.robolucha.runner.MatchRunner;
import com.robolucha.runner.luchador.LuchadorRunner;

public class OnInitAddNPC implements MatchEventListener {

	private Logger logger = Logger.getLogger(OnInitAddNPC.class);

	/**
	 * procura por NPC relacionados a gamedefinition em questao
	 */
	@Override
	public void onInit(MatchRunner runner) {

		logger.info("START add NPC to match: " + runner.getThreadName());
		logger.info("Matchrunner = " + runner);

		GameDefinition def = runner.getGameDefinition();

		Iterator<GameComponent> iterator = def.getGameComponents().iterator();
		while (iterator.hasNext()) {
			GameComponent npc = iterator.next();

			try {
				runner.add(npc);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			logger.info("gamecomponent add to the match: " + npc.getName());

		}

		logger.info("END add NPC to match: " + runner.getThreadName());
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
	public void onAlive(MatchRunner matchRunner) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onKill(MatchRunner runner, LuchadorMatchState lutchadorA, LuchadorMatchState lutchadorB) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onDamage(MatchRunner runner, LuchadorMatchState lutchadorA, LuchadorMatchState lutchadorB,
			Double amount) {
		// TODO Auto-generated method stub
		
	}

}
