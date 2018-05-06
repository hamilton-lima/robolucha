package com.robolucha.runner.luchador;

import com.robolucha.runner.MatchRunner;
import com.robolucha.test.MockLuchador;
import com.robolucha.test.MockMatchRunner;
import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

/**
 * quando adicionado move(1) e move(100) o primeiro move mais lentamente.
 * identificado que o move(1) esta levando varios ticks para ser adicionado
 * novamente a fila de comandos
 * 
 * @author hamiltonlima
 *
 */
public class BugMoveComVelocidadesDiferentesTest {

	private static Logger logger = Logger
			.getLogger(BugMoveComVelocidadesDiferentesTest.class);

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testRun() throws Exception {

		MatchRunner match = MockMatchRunner.build();
		match.getGameDefinition().setMinParticipants(1);

		match.add(MockLuchador.build(1L, MethodNames.REPEAT, "move(160);"));
		match.add(MockLuchador.build(2L, MethodNames.REPEAT,  "move(80);"));

		while (match.getRunners().size() < 2) {
			logger.debug("esperando lutchadores se preparem para o combate");
			Thread.sleep(200);
		}

		LuchadorRunner runnerA = match.getRunners().get(new Long(1L));
		LuchadorRunner runnerB = match.getRunners().get(new Long(2L));

		runnerA.getState().setX(100);
		runnerA.getState().setY(100);

		runnerB.getState().setX(100);
		runnerB.getState().setY(300);

		logger.debug("--- A : " + runnerA.getState().getPublicState());
		logger.debug("--- B : " + runnerB.getState().getPublicState());

		// start the match
		Thread t = new Thread(match);
		t.start();

		// stop the match
		Thread.sleep(500);
		match.kill();
		Thread.sleep(500);

		logger.debug("--- A depois : " + runnerA.getState().getPublicState());
		logger.debug("--- B depois : " + runnerB.getState().getPublicState());

		int dif = runnerA.getState().getPublicState().x
				- runnerB.getState().getPublicState().x;

		logger.debug("---diferenca do X  : " + dif);

		assertTrue("moveram o mesmo ?", dif == 0);

	}

}
