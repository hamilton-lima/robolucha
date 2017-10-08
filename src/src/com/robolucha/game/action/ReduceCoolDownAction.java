package com.robolucha.game.action;

import java.util.LinkedHashMap;

import org.apache.log4j.Logger;

import com.athanazio.saramago.server.dao.SaramagoColumn;
import com.robolucha.runner.LuchadorRunner;

/**
  * @author hamiltonlima
 *
 */
@SaramagoColumn(description="atualiza diversos tempos de cool down: tiro, punch e respawn do lutchador")
public class ReduceCoolDownAction implements GameAction {

	private static Logger logger = Logger.getLogger(ReduceCoolDownAction.class);

	private static ReduceCoolDownAction instance;

	private ReduceCoolDownAction() {
	}

	public static ReduceCoolDownAction getInstance() {
		if (null == instance) {
			instance = new ReduceCoolDownAction();
		}
		return instance;
	}

	public void run(LinkedHashMap<Long, LuchadorRunner> runners,
			LuchadorRunner runner) throws Exception {
		
		if( logger.isDebugEnabled()){
			logger.debug( getName() + ", lutchador=" + runner.getGameComponent().getName());
		}

		runner.updateFireCooldown();
		runner.updatePunchCooldown();
		runner.updateRespawnCooldown();
	}

	public String getName() {
		return "reduceFireCooldown";
	}

}
