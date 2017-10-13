package com.robolucha.game.action;

import java.util.LinkedHashMap;

import org.apache.log4j.Logger;

import com.robolucha.runner.LuchadorRunner;

public class TriggerEventsAction implements GameAction {

	private static Logger logger = Logger.getLogger(TriggerEventsAction.class);

	public void run(LinkedHashMap<Long, LuchadorRunner> runners,
			LuchadorRunner runner) throws Exception {
		
		logger.debug( getName() + ", luchador=" + runner.getGameComponent().getName() );
		runner.triggerEvents();
	}

	public String getName() {
		return "triggerEvents";
	}

}
