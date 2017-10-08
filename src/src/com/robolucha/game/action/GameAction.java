package com.robolucha.game.action;

import java.util.LinkedHashMap;

import com.robolucha.runner.LuchadorRunner;

public interface GameAction {

	public String getName();
	public void run(LinkedHashMap<Long, LuchadorRunner> runners, LuchadorRunner runner) throws Exception;

}
