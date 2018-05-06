package com.robolucha.game;

import com.robolucha.runner.luchador.MethodNames;
import com.robolucha.test.MockLuchador;
import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertTrue;

public class CheckTurnJuntoComTurnGunTest {

	private static Logger logger = Logger
			.getLogger(CheckTurnJuntoComTurnGunTest.class);

	@Before
	public void setUp() throws Exception {

	}

	@Test
	public void testRun() throws InterruptedException, CloneNotSupportedException {

		MatchRunner match = MockMatchRunner.build();
		match.getGameDefinition().setMinParticipants(1);
		
		Luchador a = MockLuchador.build();
		a.setId(1L);

		Code c = new Code();
		c.setEvent(MethodNames.REPEAT);
		c.setScript("move(10);turn(45);turnGun(-45);");

		List<Code> codes = new ArrayList<Code>();
		codes.add(c);

		a.getCodePackage().setCodes(codes);

		match.add(a);

		while (match.getRunners().size() < 1) {
			logger.debug("esperando lutchadores se preparem para o combate");
			Thread.sleep(200);
		}

		LuchadorRunner runnerA = match.getRunners().get(new Long(1L));

		// quase grudado no limite superior do mapa
		runnerA.getState().setX((runnerA.getSize() / 2) + 2);
		runnerA.getState().setY((runnerA.getSize() / 2) + 2);
		runnerA.getState().setGunAngle(300);

		logger.debug("--- A : " + runnerA.getState().getPublicState());
		LuchadorPublicState start = (LuchadorPublicState) runnerA.getState().getPublicState().clone();	

		// start the match
		Thread t = new Thread(match);
		t.start();

		// stop the match
		Thread.sleep(1500);
		match.kill();
		Thread.sleep(1500);

		logger.debug("--- A depois : " + runnerA.getState().getPublicState());
		LuchadorPublicState end = (LuchadorPublicState) runnerA.getState().getPublicState().clone();	

		logger.debug(String.format("*** resultados angle : a[%s, %s]",
				start.angle, end.angle));
		
		logger.debug(String.format("*** resultados gun angle : a[%s, %s]",
				start.gunAngle, end.gunAngle));

		assertTrue("verifica se lutchador girou ", end.angle > start.angle);

		assertTrue("verifica se lutchador girou arma ",
				end.gunAngle < start.gunAngle);

	}
}
