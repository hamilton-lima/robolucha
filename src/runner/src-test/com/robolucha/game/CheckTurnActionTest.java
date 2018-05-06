package com.robolucha.game;

import com.robolucha.test.MockLuchador;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class CheckTurnActionTest {

	private static Logger logger = Logger.getLogger(CheckTurnActionTest.class);

	@Before
	public void setUp() throws Exception {

		logger.setLevel(Level.DEBUG);
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
		c.setScript("turn(45);");
		List<Code> codes = new ArrayList<Code>();
		codes.add(c);

		a.getCodePackage().setCodes(codes);

		Code c1 = new Code();
		c1.setEvent("repeat");
		c1.setScript("turn(-45);");
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

		double startA = 20;
		double startB = 350;

		runnerA.getState().setX(100);
		runnerA.getState().setY(100);
		runnerA.getState().setAngle(startA);

		runnerB.getState().setX(100);
		runnerB.getState().setY(250);
		runnerB.getState().setAngle(startB);

		double angleA1 = runnerA.getState().getPublicState().angle;
		double angleB1 = runnerB.getState().getPublicState().angle;

		logger.debug("--- A : " + runnerA.getState().getPublicState());
		logger.debug("--- B : " + runnerB.getState().getPublicState());

		// start the match
		Thread t = new Thread(match);
		t.start();

		// stop the match
		Thread.sleep(1500);
		match.kill();
		Thread.sleep(1500);

		logger.debug("--- A depois : " + runnerA.getState().getPublicState());
		logger.debug("--- B depois : " + runnerB.getState().getPublicState());

		double angleA2 = runnerA.getState().getPublicState().angle;
		double angleB2 = runnerB.getState().getPublicState().angle;

		logger.debug(String.format("*** resultados : a[%s, %s], b[%s, %s]",
				angleA1, angleA2, angleB1, angleB2));

		assertTrue("verifica se turn funcionou para A", angleA1 < angleA2);
		assertTrue("verifica se turn funcionou para B", angleB1 > angleB2);

		double diffA = angleA2 - angleA1;
		double diffB = angleB2 - angleB1;

		logger.debug(String.format("*** resultados dif A: %s, dif B: %s",
				diffA, diffB));
		
		// accept up to 1 degree as difference
		assertEquals( diffA, diffB *-1, 1.0);

	}
}