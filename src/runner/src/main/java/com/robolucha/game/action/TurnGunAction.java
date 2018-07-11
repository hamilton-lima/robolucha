package com.robolucha.game.action;

import java.util.LinkedHashMap;

import org.apache.log4j.Logger;

import com.robolucha.runner.luchador.LuchadorRunner;

/*
 * @deprecated use ChangeStateAction instead
 * @see ChangeStateAction
 */
public class TurnGunAction implements GameAction {

	private static Logger logger = Logger.getLogger(TurnGunAction.class);

	private static TurnGunAction instance;

	private TurnGunAction() {
	}

	public static TurnGunAction getInstance() {
		if (null == instance) {
			instance = new TurnGunAction();
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
		return LuchadorRunner.COMMAND_TURNGUN;
	}

}
