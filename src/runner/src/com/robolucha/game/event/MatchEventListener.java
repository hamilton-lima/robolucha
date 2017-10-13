package com.robolucha.game.event;

import com.robolucha.runner.LuchadorRunner;
import com.robolucha.runner.MatchRunner;

public interface MatchEventListener {

	public void onInit(MatchRunner runner);

	public void onStart(MatchRunner runner);

	public void onEnd(MatchRunner runner);

	public void onKill(MatchRunner runner, LuchadorRunner lutchadorA,
			LuchadorRunner lutchadorB);

	public void onDamage(MatchRunner runner, LuchadorRunner lutchadorA,
			LuchadorRunner lutchadorB, Double amount);
	
	public void onAlive(MatchRunner matchRunner);

}
