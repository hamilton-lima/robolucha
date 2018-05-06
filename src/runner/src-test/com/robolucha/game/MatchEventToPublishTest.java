package com.robolucha.game;

import com.athanazio.saramago.server.util.JSONUtil;
import com.robolucha.api.StartGame;
import com.robolucha.game.vo.EventVO;
import com.robolucha.game.vo.MatchRunStateVO;
import com.robolucha.publisher.MatchRunStateKeeper;
import com.robolucha.test.MockLuchador;
import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertTrue;

public class MatchEventToPublishTest {

	private static Logger logger = Logger
			.getLogger(MatchEventToPublishTest.class);

	@Before
	public void setUp() throws Exception {

	}

	private class LoopControl {
		boolean active = true;
		EventVO event = null;
	}

	@Test
	public void testKill() throws InterruptedException {

		final MatchRunner match = MockMatchRunner.build();
		final LoopControl watching = new LoopControl();

		StartGame job = new StartGame();
		Thread t = job.buildRunner(match);

		Thread stateWatcher = new Thread(new Runnable() {

			public void run() {
				while (watching.active) {
					MatchRunStateVO state = MatchRunStateKeeper.getInstance().get(
							match.getMatch().getId());
					
					String json = JSONUtil.toJSON(state);
					
					logger.debug("json = " + json);

					// recebidos dados do evento
					// armazenar !!!
					if (json.contains("KILL")) {
						
						logger.debug("*********** FOUND A KILL");						
						
						MatchRunStateVO vo = (MatchRunStateVO) JSONUtil
								.fromJSON(json, MatchRunStateVO.class);
						watching.event = vo.events.get(0);
					}

					try {
						Thread.sleep(10);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		});

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
		runnerA.getState().setLife(1);

		runnerB.getState().setX(100);
		runnerB.getState().setY(150);
		runnerB.getState().setGunAngle(-90);
		runnerB.getState().setLife(1);

		logger.debug("--- A : " + runnerA.getState().getPublicState());
		logger.debug("--- B : " + runnerB.getState().getPublicState());

		stateWatcher.start();
		t.start();

		// stop the match
		Thread.sleep(500);
		match.kill();
		Thread.sleep(500);
		watching.active = false;

		logger.debug("--- A depois : " + runnerA.getState().getPublicState());
		logger.debug("--- B depois : " + runnerB.getState().getPublicState());

		logger.debug("** evento recebido : " + watching.event);

		assertTrue("verifica se existe event", watching.event != null);
		assertTrue("verifica se event = KILL ",
				"KILL".equals(watching.event.event));

	}
}
