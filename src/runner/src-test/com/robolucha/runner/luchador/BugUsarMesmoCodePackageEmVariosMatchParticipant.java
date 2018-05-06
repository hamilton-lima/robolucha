package com.robolucha.runner.luchador;

import com.athanazio.saramago.service.Response;
import com.robolucha.bean.MatchParticipant;
import com.robolucha.bean.MatchRun;
import com.robolucha.service.LuchadorCrudService;
import com.robolucha.service.MatchParticipantCrudService;
import com.robolucha.test.MockLuchador;
import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class BugUsarMesmoCodePackageEmVariosMatchParticipant {

	private static Logger logger = Logger.getLogger(BugUsarMesmoCodePackageEmVariosMatchParticipant.class);
	BeanMockDataGenerator generator;

	@Before
	public void setUp() throws Exception {


		generator = new BeanMockDataGenerator() {
			public Class getClassToTest() {
				return MatchRun.class;
			}
		};
		generator.setup();
	}

	@Test
	public void test() throws InstantiationException, IllegalAccessException {
		Luchador luchador = MockLuchador.createWithRepeatCode("move(10)");
		LuchadorCrudService.getInstance().doSaramagoAdd(luchador, new Response());
		
		MatchParticipant participant = new MatchParticipant();

		participant.setMatchRun((MatchRun) generator.mock());
		participant.setLuchador(luchador);
		participant.getCodePackages().add(luchador.getCodePackage());
		participant.setTimeStart(System.currentTimeMillis());
		Response response = MatchParticipantCrudService.getInstance().doSaramagoAdd(participant, new Response());
		logger.debug(response);

		assertTrue("sem erros para inserir", response.getErrors().size() == 0);

		MatchParticipant participant2 = new MatchParticipant();

		participant2.setMatchRun((MatchRun) generator.mock());
		participant2.setLuchador(luchador);
		participant2.getCodePackages().add(luchador.getCodePackage());
		participant2.setTimeStart(System.currentTimeMillis());
		Response response2 = MatchParticipantCrudService.getInstance().doSaramagoAdd(participant2, new Response());
		logger.debug(response);

		assertTrue("sem erros para inserir (2)", response2.getErrors().size() == 0);

	}

}
