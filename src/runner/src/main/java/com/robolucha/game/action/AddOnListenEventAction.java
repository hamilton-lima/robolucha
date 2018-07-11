package com.robolucha.game.action;

import java.util.LinkedHashMap;

import org.apache.log4j.Logger;

import com.robolucha.game.event.OnListenEvent;
import com.robolucha.models.LuchadorPublicState;
import com.robolucha.runner.luchador.LuchadorRunner;

/**
 * @author hamiltonlima
 *
 */
public class AddOnListenEventAction implements GameAction {

	private static Logger logger = Logger
			.getLogger(AddOnListenEventAction.class);

	private OnListenEvent event;


	public AddOnListenEventAction(LuchadorPublicState source, String message) {
		this.event = new OnListenEvent(source, message);
	}

	public void run(LinkedHashMap<Long, LuchadorRunner> runners,
			LuchadorRunner runner) throws Exception {

		if (logger.isDebugEnabled()) {
			logger.debug(getName() + ", lutchador="
					+ runner.getGameComponent().getName());
		}

		runner.addEvent(event);
	}

	public String getName() {
		return "AddOnListenEventAction";
	}

}
