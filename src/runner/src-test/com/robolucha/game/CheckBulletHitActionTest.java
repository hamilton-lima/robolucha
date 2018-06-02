package com.robolucha.game;

import com.robolucha.game.event.LuchadorEvent;
import com.robolucha.game.event.LuchadorEventListener;
import com.robolucha.game.event.OnGotDamageEvent;
import com.robolucha.models.Luchador;
import com.robolucha.runner.MatchRunner;
import com.robolucha.runner.luchador.LuchadorRunner;
import com.robolucha.test.MockLuchador;
import com.robolucha.test.MockMatchRunner;
import io.reactivex.functions.Consumer;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;


public class CheckBulletHitActionTest {

	private static Logger logger = Logger.getLogger(CheckRadarActionTest.class);
	private static int counter = 0;

	@Before
	public void setUp() throws Exception {
		logger.setLevel(Level.ALL);
		counter = 0;
	}

	@Test
	public void testRun() throws Exception {

		MatchRunner match = MockMatchRunner.build();

		Luchador a = MockLuchador.build(1L, "repeat", "fire(1);");
		Luchador b = MockLuchador.build(2L, "repeat", "fire(10);");

		match.add(a);
		match.add(b);

		// adiciona listener para receber evento de onfound ...
		match.addListener(new LuchadorEventListener() {

			public void listen(LuchadorEvent event) {
				logger.info(">>> event : " + event);

				if (event instanceof OnGotDamageEvent) {
					logger.debug("GOT DAMAGE !! : " + event);
					counter++;
				}
			}
		});


		match.getMatchStart().blockingSubscribe(new Consumer<Long>() {
			public void accept(Long aLong) throws Exception {

				LuchadorRunner runnerA = match.getRunners().get(new Long(1L));
				LuchadorRunner runnerB = match.getRunners().get(new Long(2L));

				runnerA.getState().setX(100);
				runnerA.getState().setY(100);
				runnerA.getState().setAngle(90);
				runnerA.getState().setGunAngle(90);

				runnerB.getState().setX(100);
				runnerB.getState().setY(150);
				runnerB.getState().setGunAngle(-90);

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

				assertEquals("verifica se lutchador abaixo do outro recebeu o tiro",
						19.0, runnerB.getState().getLife(), 0.001);

				assertEquals("verifica se lutchador acima do outro recebeu o tiro", 10.0,
						runnerA.getState().getLife(), 0.001);
			}
		});

	}
}
