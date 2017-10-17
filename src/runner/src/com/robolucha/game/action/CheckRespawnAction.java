package com.robolucha.game.action;

import java.util.LinkedHashMap;

import org.apache.log4j.Logger;

import com.robolucha.runner.LuchadorRunner;
import com.robolucha.runner.MatchRunner;
import com.robolucha.runner.RespawnPoint;

public class CheckRespawnAction implements GameAction {

	private static Logger logger = Logger.getLogger(CheckRespawnAction.class);
	private MatchRunner matchRunner;

	public CheckRespawnAction(MatchRunner matchRunner) {
		this.matchRunner = matchRunner;
	}

	public void run(LinkedHashMap<Long, LuchadorRunner> runners,
			LuchadorRunner runner) {

		if (logger.isDebugEnabled()) {
			logger.debug("*** " + getName() + ", lutchador="
					+ runner.getGameComponent());

			logger.debug("*** " + getName() + ", isActive=" + runner.isActive());
			logger.debug("*** " + getName() + ", getLastRunningError="
					+ runner.getLastRunningError());

			logger.debug("*** " + getName() + ", getRespawnCoolDown="
					+ runner.getRespawnCoolDown());

		}

		// nao esta ativo
		if (!runner.isActive()) {

			// foi desativado porque morreu
			if (LuchadorRunner.DEAD.equals(runner.getLastRunningError())) {

				// acabou o tempo para o respawn
				if (runner.getRespawnCoolDown() <= 0) {

					RespawnPoint point = matchRunner.getRespawnPoint(runner);
					runner.respawn(point);

				}

			}

		}
	}

	public String getName() {
		return "checkrespawn";
	}

}
