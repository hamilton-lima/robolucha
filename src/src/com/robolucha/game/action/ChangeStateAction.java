package com.robolucha.game.action;

import java.util.LinkedHashMap;

import org.apache.log4j.Logger;

import com.robolucha.runner.LuchadorRunner;

public class ChangeStateAction implements GameAction {
	public static String NAME = "ChangeState";

	private static Logger logger = Logger.getLogger(ChangeStateAction.class);

	public void run(LinkedHashMap<Long, LuchadorRunner> runners, LuchadorRunner runner) {
		if (logger.isDebugEnabled()) {
			logger.debug(getName() + ", lutchador=" + runner.getGameComponent().getName());
		}

		runner.consumeCommand();
	}


	public String getName() {
		return NAME;
	}

}
