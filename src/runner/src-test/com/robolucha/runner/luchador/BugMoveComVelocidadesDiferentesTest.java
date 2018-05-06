package com.robolucha.runner.luchador;

import com.robolucha.test.MockLuchador;
import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

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
	public void testRun() throws InterruptedException {

		MatchRunner match = MockMatchRunner.build();
		match.getGameDefinition().setMinParticipants(1);

		match.add(buildLuchador(1L, "move(160);"));
		match.add(buildLuchador(2L, "move(80);"));

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

	private Luchador buildLuchador(long id, String repeatCode) {
		Luchador a = MockLuchador.build();
		a.setId(id);

		Code c = new Code();
		c.setEvent(MethodNames.REPEAT);
		c.setScript(repeatCode);
		List<Code> codes = new ArrayList<Code>();
		codes.add(c);

		a.getCodePackage().setCodes(codes);
		return a;
	}
}
