package com.robolucha.game.event;

import com.robolucha.models.LuchadorMatchState;
import com.robolucha.runner.MatchRunner;

public interface MatchEventListener {

	public void onInit(MatchRunner runner);

	public void onStart(MatchRunner runner);

	public void onEnd(MatchRunner runner);

	public void onKill(MatchRunner runner, LuchadorMatchState lutchadorA,
			LuchadorMatchState lutchadorB);

	public void onDamage(MatchRunner runner, LuchadorMatchState lutchadorA,
			LuchadorMatchState lutchadorB, Double amount);
	
	public void onAlive(MatchRunner matchRunner);

}
