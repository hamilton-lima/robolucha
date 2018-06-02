package com.robolucha.game;

import com.robolucha.models.Luchador;
import com.robolucha.runner.MatchRunner;
import com.robolucha.runner.luchador.LuchadorRunner;
import com.robolucha.runner.luchador.MethodNames;
import com.robolucha.test.MockLuchador;
import com.robolucha.test.MockMatchRunner;
import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;

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
	public void testRun() throws Exception {

		MatchRunner match = MockMatchRunner.build();

		Luchador a = MockLuchador.build(1L, MethodNames.REPEAT, "turnGun(10);");
		Luchador b = MockLuchador.build(2L, MethodNames.REPEAT, "turnGun(-10);");

		match.add(a);
		match.add(b);

		match.getMatchStart()
				.blockingSubscribe(onStart -> {
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

				});



	}
}
