package com.robolucha.runner;

import com.robolucha.models.GameComponent;
import com.robolucha.models.Luchador;
import com.robolucha.models.LuchadorCoach;
import com.robolucha.models.LuchadorMask;
import com.robolucha.models.MaskConfig;
import com.robolucha.models.MatchParticipant;

//TODO: Implement the API calls!!!
public class MatchRunnerAPI {

	private MatchRunnerAPI() {}
	private static MatchRunnerAPI instance  = new MatchRunnerAPI();
	public static MatchRunnerAPI getInstance() {
		return instance;
	}
	public LuchadorCoach findCoach(String facebookID) {
		// TODO Auto-generated method stub
		return null;
	}
	public LuchadorCoach addCoach(String name, String email) {
		// TODO Auto-generated method stub
		return null;
	}
	public Luchador addLuchador(Luchador luchador) {
		// TODO Auto-generated method stub
		return null;
	}
	public void saveMask(GameComponent gameComponent, MaskConfig mask) {
		// TODO Auto-generated method stub
		
	}
	public void addMatchParticipant(MatchParticipant matchParticipant) {
		// TODO Auto-generated method stub
		
	}
	public MaskConfig findMask(GameComponent gameComponent) {
		// TODO Auto-generated method stub
		return null;
	}

	
	
}
