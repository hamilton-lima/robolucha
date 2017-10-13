package com.robolucha.runner;

import java.util.ArrayList;
import java.util.List;

import com.robolucha.runner.models.GameComponent;
import com.robolucha.runner.models.MatchParticipant;
import com.robolucha.runner.models.Match;

public class MatchRunnerValidationHelper {
	private static MatchRunnerValidationHelper instance;

	private MatchRunnerValidationHelper() {
	}

	public static MatchRunnerValidationHelper getInstance() {
		if (null == instance) {
			instance = new MatchRunnerValidationHelper();
		}
		return instance;
	}

	/**
	 * retorna a partida atual em que o luchador esta
	 * 
	 * @param component
	 * @return
	 */
	public Match currentMatchFromLuchador(GameComponent component) {
		if( component == null){
			return null;
		}
		
		String oql = "select A from MatchParticipant as A WHERE matchRun.timeEnd < matchRun.timeStart and luchador.id = "
				+ component.getId();
		
		//TODO: make remote call
		List list = new ArrayList(); // GenericDAO.getInstance().findAll(oql);
		if (list.size() > 0) {
			MatchParticipant matchParticipant = (MatchParticipant) list.get(0);
			return matchParticipant.getMatchRun();
		}
		return null;
	}

}
