package com.robolucha.game;

import com.robolucha.game.event.LuchadorEvent;
import com.robolucha.game.event.LuchadorEventListener;
import com.robolucha.game.event.OnGotDamageEvent;
import com.robolucha.test.MockLuchador;
import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class CheckBulletHitActionTest {

	private static Logger logger = Logger.getLogger(CheckRadarActionTest.class);
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
		c.setScript("fire(1);");
		List<Code> codes = new ArrayList<Code>();
		codes.add(c);

		a.getCodePackage().setCodes(codes);

		Code c1 = new Code();
		c1.setEvent("repeat");
		c1.setScript("fire(10);");
		List<Code> codes1 = new ArrayList<Code>();
		codes1.add(c1);

		b.getCodePackage().setCodes(codes1);

		match.add(a);
		match.add(b);

		// adiciona listener para receber evento de onfound ...
		match.addListener(new LuchadorEventListener() {

			public void listen(LuchadorEvent event) {
				logger.debug(">>> event : " + event);

				if (event instanceof OnGotDamageEvent) {
					logger.debug("GOT DAMAGE !! : " + event);
					counter++;
				}
			}
		});

		while (match.getRunners().size() < 2) {
			logger.debug("esperando lutchadores se preparem para o combate");
			Thread.sleep(200);
		}

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
}
