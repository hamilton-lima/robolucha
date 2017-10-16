package com.robolucha.game.action;

import java.util.LinkedHashMap;

import org.apache.log4j.Logger;

import com.robolucha.runner.LuchadorRunner;
import com.robolucha.runner.MethodNames;

/**
  * @author hamiltonlima
 *
 */
public class RepeatAction implements GameAction {

	private static Logger logger = Logger.getLogger(RepeatAction.class);
	
	public void run(LinkedHashMap<Long, LuchadorRunner> runners,
			LuchadorRunner runner) throws Exception {
		
		logger.debug( getName() + ", lutchador=" + runner.getGameComponent().getId());
		runner.run(getName());
	}

	public String getName() {
		return MethodNames.REPEAT;
	}

}
