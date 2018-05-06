package com.robolucha.runner.luchador;

import com.robolucha.test.MockLuchador;
import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertTrue;

/**
 * atraves da variavel me com possibilidade de posicionar o lutchador
 * arbitrariamente.
 * 
 * @author hamiltonlima
 *
 */
public class BugAcessoVariavelMeTest {

	private static Logger logger = Logger.getLogger(BugAcessoVariavelMeTest.class);

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testRun() throws InterruptedException {

		MatchRunner match = MockMatchRunner.build();
		match.getGameDefinition().setMinParticipants(1);

		Luchador a = MockLuchador.build();
		a.setId(1L);

		Code c = new Code();
		c.setEvent(MethodNames.REPEAT);
		c.setScript("me.x = 200;");
		List<Code> codes = new ArrayList<Code>();
		codes.add(c);

		a.getCodePackage().setCodes(codes);

		match.add(a);

		while (match.getRunners().size() < 1) {
			logger.debug("esperando lutchadores se preparem para o combate");
			Thread.sleep(200);
		}

		LuchadorRunner runnerA = match.getRunners().get(new Long(1L));

		runnerA.getState().setX(100);
		runnerA.getState().setY(100);

		logger.debug("--- A : " + runnerA.getState().getPublicState());
		logger.debug("--- A : " + runnerA.getState());

		// start the match
		Thread t = new Thread(match);
		t.start();

		// stop the match
		Thread.sleep(2500);
		match.kill();
		Thread.sleep(500);

		logger.debug("--- A depois : " + runnerA.getState().getPublicState());
		logger.debug("--- A depois : " + runnerA.getState());

		assertTrue("verifica se lutchador ficou no lugar certo ...", runnerA.getState().getX() == 100);

	}
}
