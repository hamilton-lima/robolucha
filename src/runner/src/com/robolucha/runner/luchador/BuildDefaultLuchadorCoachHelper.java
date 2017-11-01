package com.robolucha.runner.luchador;

import java.util.List;

import org.apache.log4j.Logger;

import com.robolucha.models.Code;
import com.robolucha.models.GameComponent;
import com.robolucha.models.Luchador;
import com.robolucha.models.LuchadorCoach;
import com.robolucha.models.MaskConfig;
import com.robolucha.runner.MatchRunnerAPI;
import com.robolucha.runner.luchador.mask.MaskGenerator;

public class BuildDefaultLuchadorCoachHelper {

	private static Logger logger = Logger.getLogger(BuildDefaultLuchadorCoachHelper.class);

	public static LuchadorCoach buildDefaultLuchadorCoach(String name, String email, String facebookID)
			throws Exception {

		LuchadorCoach luchadorCoach = MatchRunnerAPI.getInstance().findCoach(facebookID);
		logger.debug("coach loaded: " + luchadorCoach);

		if (luchadorCoach == null) {

			try {
				luchadorCoach = MatchRunnerAPI.getInstance().addCoach(name, email);
				buildLuchador(luchadorCoach);
			} catch (Exception e) {
				logger.error("erro criando cliente", e);
			}
		}

		return luchadorCoach;
	}

	public static void buildLuchador(LuchadorCoach coach) throws Exception {
		Luchador luchador = new Luchador();
		luchador.setCoach(coach);

		String name = "luchador" + coach.getId();
		luchador.setName(name);

		addCodeToLutchador(luchador);
		addRandomMaskToGameComponent(luchador);

		Luchador result = MatchRunnerAPI.getInstance().addLuchador(luchador);
		logger.debug("**** luchador : " + result);
	}

	public static MaskConfig addRandomMaskToGameComponent(GameComponent gameComponent) {
		MaskConfig mask = MaskGenerator.getInstance().random();
		MatchRunnerAPI.getInstance().saveMask(gameComponent, mask);
		return mask;
	}

	private static void addCodeToLutchador(Luchador luchador) throws Exception {
		List<Code> codes = ScriptDefinitionFactory.getInstance().getDefault().getLuchadorFirstCode();
		luchador.setCodes(codes);

	}

}
