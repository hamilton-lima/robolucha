package com.robolucha.game.action;

import java.util.LinkedHashMap;

import org.apache.log4j.Logger;

import com.robolucha.runner.luchador.LuchadorRunner;

/*
 * @deprecated use ChangeStateAction instead
 * @see ChangeStateAction
 */
public class MoveAction implements GameAction {

	private static Logger logger = Logger.getLogger(MoveAction.class);

	private static MoveAction instance;

	private MoveAction() {
	}

	public static MoveAction getInstance() {
		if (null == instance) {
			instance = new MoveAction();
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
		return LuchadorRunner.COMMAND_MOVE;
	}

}
