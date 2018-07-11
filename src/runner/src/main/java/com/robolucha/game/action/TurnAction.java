package com.robolucha.game.action;

import java.util.LinkedHashMap;

import org.apache.log4j.Logger;

import com.robolucha.runner.luchador.LuchadorRunner;

/*
 * @deprecated use ChangeStateAction instead
 * @see ChangeStateAction
 */
public class TurnAction implements GameAction {

	private static Logger logger = Logger.getLogger(TurnAction.class);

	private static TurnAction instance;

	private TurnAction() {
	}

	public static TurnAction getInstance() {
		if (null == instance) {
			instance = new TurnAction();
		}
		return instance;
	}

	public void run(LinkedHashMap<Long, LuchadorRunner> runners,
			LuchadorRunner runner) {

		logger.debug(getName() + ", lutchador="
				+ runner.getGameComponent().getName());
		//runner.consumeCommand(getName());
	}

	public String getName() {
		return LuchadorRunner.COMMAND_TURN;
	}
}
