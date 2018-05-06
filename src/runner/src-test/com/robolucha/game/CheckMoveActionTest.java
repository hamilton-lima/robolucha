package com.robolucha.game;

import com.robolucha.test.MockLuchador;
import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertTrue;

public class CheckMoveActionTest {

	private static Logger logger = Logger.getLogger(CheckMoveActionTest.class);
	private static int counter = 0;

	@Before
	public void setUp() throws Exception {

		counter = 0;
	}

	@Test
	public void testRun() throws InterruptedException {

		MatchRunner match = MockMatchRunner.build();

		Luchador a = MockLuchador.build();
		a.setId(1L);

		Luchador b = MockLuchador.build();
		b.setId(2L);

		Code c = new Code();
		c.setEvent("repeat");
		c.setScript("move(10);");
		List<Code> codes = new ArrayList<Code>();
		codes.add(c);

		a.getCodePackage().setCodes(codes);

		Code c1 = new Code();
		c1.setEvent("repeat");
		c1.setScript("move(-10);");
		List<Code> codes1 = new ArrayList<Code>();
		codes1.add(c1);

		b.getCodePackage().setCodes(codes1);

		match.add(a);
		match.add(b);

		while (match.getRunners().size() < 2) {
			logger.debug("esperando lutchadores se preparem para o combate");
			Thread.sleep(200);
		}

		LuchadorRunner runnerA = match.getRunners().get(new Long(1L));
		LuchadorRunner runnerB = match.getRunners().get(new Long(2L));

		runnerA.getState().setX(100);
		runnerA.getState().setY(100);

		runnerB.getState().setX(100);
		runnerB.getState().setY(250);

		double aX1 = runnerA.getState().getPublicState().x;
		double bX1 = runnerB.getState().getPublicState().x;

		logger.debug("--- A : " + runnerA.getState().getPublicState());
		logger.debug("--- B : " + runnerB.getState().getPublicState());

		// start the match
		Thread t = new Thread(match);
		t.start();

		// stop the match
		Thread.sleep(1500);
		match.kill();
		Thread.sleep(500);

		logger.debug("--- A depois : " + runnerA.getState().getPublicState());
		logger.debug("--- B depois : " + runnerB.getState().getPublicState());

		double aX2 = runnerA.getState().getPublicState().x;
		double bX2 = runnerB.getState().getPublicState().x;

		logger.debug(String.format("*** resultados : a[%s, %s], b[%s, %s]",
				aX1, aX2, bX1, bX2));

		assertTrue("verifica se lutchador moveu A", aX2 > aX1);
		assertTrue("verifica se lutchador moveu B", bX2 < bX1);

	}
}
