package com.robolucha.game;

import com.robolucha.test.MockLuchador;
import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertTrue;

public class CheckTurnGunActionTest {

	private static Logger logger = Logger
			.getLogger(CheckTurnGunActionTest.class);
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
		c.setScript("turnGun(10);");
		List<Code> codes = new ArrayList<Code>();
		codes.add(c);

		a.getCodePackage().setCodes(codes);

		Code c1 = new Code();
		c1.setEvent("repeat");
		c1.setScript("turnGun(-10);");
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
		runnerA.getState().setGunAngle(180);

		runnerB.getState().setX(100);
		runnerB.getState().setY(150);
		runnerB.getState().setGunAngle(90);

		double gunAngleA1 = runnerA.getState().getPublicState().gunAngle;
		double gunAngleB1 = runnerB.getState().getPublicState().gunAngle;

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

		double gunAngleA2 = runnerA.getState().getPublicState().gunAngle;
		double gunAngleB2 = runnerB.getState().getPublicState().gunAngle;

		logger.debug(String.format("*** resultados : a[%s, %s], b[%s, %s]",
				gunAngleA1, gunAngleA2, gunAngleB1, gunAngleB2));

		assertTrue("verifica se turnGun funcionou para A", gunAngleA1 < gunAngleA2);
		assertTrue("verifica se turnGun funcionou para B", gunAngleB1 > gunAngleB2);

	}
}
