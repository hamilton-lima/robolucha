package com.robolucha.game.action;

import java.util.LinkedHashMap;

import org.apache.log4j.Logger;

import com.robolucha.runner.LuchadorRunner;

/*
 * @deprecated use ChangeStateAction instead
 * @see ChangeStateAction
 */
public class FireAction implements GameAction {

	private static Logger logger = Logger.getLogger(FireAction.class);

	private static FireAction instance;

	private FireAction() {
	}

	public static FireAction getInstance() {
		if (null == instance) {
			instance = new FireAction();
		}
		return instance;
	}

	public void run(LinkedHashMap<Long, LuchadorRunner> runners,
			LuchadorRunner runner) {
		logger.debug(getName() + ", lutchador="
				+ runner.getGameComponent().getName());
		
		// runner.consumeCommand(getName());

	}

	@Override
	public String getName() {
		return LuchadorRunner.COMMAND_FIRE;
	}

}
