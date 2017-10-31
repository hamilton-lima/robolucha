package com.robolucha.runner.luchador;

import org.apache.log4j.Logger;

import com.athanazio.saramago.server.dao.GenericDAO;
import com.athanazio.saramago.server.util.JSONUtil;
import com.athanazio.saramago.service.Response;
import com.robolucha.models.Code;
import com.robolucha.models.GameComponent;
import com.robolucha.models.Luchador;
import com.robolucha.models.LuchadorCoach;
import com.robolucha.models.MaskConfig;
import com.robolucha.runner.MatchRunnerAPI;
import com.robolucha.runner.luchador.mask.MaskGenerator;
import com.robolucha.service.CodeCrudService;
import com.robolucha.service.LuchadorCrudService;

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
		String name = "luchador" + coach.getId();
		Luchador result = MatchRunnerAPI.getInstance().addLuchador(name, coach);

		addCodeToLutchador(result);
		addRandomMaskToGameComponent(result);

		logger.debug("**** luchador : " + result);

	}

	public static MaskConfig addRandomMaskToGameComponent(GameComponent gameComponent) {
		MaskConfig mask = MaskGenerator.getInstance().random();
		MatchRunnerAPI.getInstance().saveMask(gameComponent, mask);
		return mask;
	}

	//TODO: get default code from scriptDefinition
	private static void addCodeToLutchador(Luchador luchador) throws Exception {
		Code c1 = new Code();
		c1.setEvent(MethodNames.REPEAT);
		c1.setScript("move(20);\nfire(1);");
		c1.setVersion(1L);

		Response r = CodeCrudService.getInstance().doSaramagoAdd(c1, new Response());
		logger.debug("*** criacao de code : " + r);
		luchador.getCodePackage().getCodes().add((Code) r.getData());

		c1 = new Code();
		c1.setEvent(MethodNames.ON_HIT_WALL);
		c1.setScript("turn(45);");
		c1.setVersion(1L);

		r = CodeCrudService.getInstance().doSaramagoAdd(c1, new Response());
		logger.debug("*** criacao de code : " + r);
		luchador.getCodePackage().getCodes().add((Code) r.getData());

		Response response = new Response();
		LuchadorCrudService.getInstance().doSaramagoUpdate(luchador, response);
		if (response.getErrors().size() > 0) {
			throw new Exception(JSONUtil.toJSON(response.getErrors()));
		}
	}

}
