package com.robolucha.game.action;

import java.util.LinkedHashMap;

import org.apache.log4j.Logger;

import com.robolucha.runner.LuchadorRunner;
import com.robolucha.runner.MatchRunner;

public class RemoveDeadAction implements GameAction {

	private static Logger logger = Logger.getLogger(RemoveDeadAction.class);
	private MatchRunner matchRunner;

	public RemoveDeadAction(MatchRunner matchRunner) {
		this.matchRunner = matchRunner;
	}

	public void run(LinkedHashMap<Long, LuchadorRunner> runners,
			LuchadorRunner runner) {

		logger.debug("RemoveDeadAction, lutchador=" + runner.getGameComponent());

		if (runner.isActive()) {
			if (runner.getState().getLife() <= 0) {
				runner.kill();
				logger.debug("RemoveDeadAction, KILLED");
			}
		}
	}

	public String getName() {
		return "removeDead";
	}
}
