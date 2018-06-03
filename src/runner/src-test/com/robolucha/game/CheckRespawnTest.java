package com.robolucha.game;

import com.robolucha.models.Code;
import com.robolucha.models.Luchador;
import com.robolucha.runner.MatchRunner;
import com.robolucha.runner.luchador.LuchadorRunner;
import com.robolucha.runner.luchador.MethodNames;
import com.robolucha.test.MockLuchador;
import com.robolucha.test.MockMatchRunner;
import org.apache.log4j.Logger;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class CheckRespawnTest {

	private static Logger logger = Logger.getLogger(CheckRadarActionTest.class);

	@Test
	public void testRun() throws Exception {

		MatchRunner match = MockMatchRunner.build();
		match.getGameDefinition().setMinParticipants(1);

		Luchador a = MockLuchador.build();
		a.setId(1L);

		Luchador b = MockLuchador.build();
		b.setId(2L);

		Code c = new Code();
		c.setEvent(MethodNames.REPEAT);
		c.setScript("fire(2);");
		a.getCodes().add(c);

		match.add(a);
		match.add(b);

		match.getMatchStart()
				.blockingSubscribe(onStart -> {
					LuchadorRunner runnerA = match.getRunners().get(new Long(1L));
					LuchadorRunner runnerB = match.getRunners().get(new Long(2L));

					runnerA.getState().setX(100);
					runnerA.getState().setY(100);
					runnerA.getState().setAngle(90);
					runnerA.getState().setGunAngle(90);

					runnerB.getState().setX(100);
					runnerB.getState().setY(150);
					runnerB.getState().setGunAngle(-90);
					runnerB.getState().setLife(1);

					logger.debug("--- A : " + runnerA.getState().getPublicState());
					logger.debug("--- B : " + runnerB.getState().getPublicState());

					// start the match
					Thread t = new Thread(match);
					t.start();

					// stop the match
					Thread.sleep((long) a.getRespawnCooldown());

					match.kill();
					Thread.sleep(500);

					logger.debug("--- A depois : " + runnerA.getState().getPublicState());
					logger.debug("--- B depois : " + runnerB.getState().getPublicState());

					assertTrue("verifica se lutchador esta ativo apos ter sido eliminado",
							runnerA.isActive());
					assertTrue("verifica se lutchador esta ativo apos ter sido eliminado",
							runnerB.isActive());

				});
	}
}
