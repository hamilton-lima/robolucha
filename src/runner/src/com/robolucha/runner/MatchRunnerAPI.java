package com.robolucha.runner;

import com.robolucha.models.GameComponent;
import com.robolucha.models.GameDefinition;
import com.robolucha.models.Luchador;
import com.robolucha.models.LuchadorCoach;
import com.robolucha.models.LuchadorMask;
import com.robolucha.models.MaskConfig;
import com.robolucha.models.Match;
import com.robolucha.models.MatchEvent;
import com.robolucha.models.MatchParticipant;
import com.robolucha.models.MatchScore;

//TODO: MUST DO - Implement the API calls!!!
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

	public Match createMatch(GameDefinition gameDefinition) {
		// TODO Auto-generated method stub
		return null;
	}
	public void updateMatch(Match match) throws Exception{
		// TODO Auto-generated method stub
		
	}
	public MatchScore findScore(Match match, GameComponent gameComponent) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
	public void updateScore(MatchScore score) throws Exception {
		// TODO Auto-generated method stub
		
	}
	public void addScore(MatchScore score) throws Exception{
		// TODO Auto-generated method stub
		
	}
	public Match findMatchById(Long id) {
		// TODO Auto-generated method stub
		return null;
	}
	public void addMatchEvent(MatchEvent event) {
		// TODO Auto-generated method stub
		
	}

	
	
}
