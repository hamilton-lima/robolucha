package com.robolucha.game;

import com.robolucha.test.MockLuchador;
import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class LuchadorConstantsTest {

	private static Logger logger = Logger
			.getLogger(LuchadorConstantsTest.class);

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testRun() throws InterruptedException {

		MatchRunner match = MockMatchRunner.build();

		Luchador a = MockLuchador.build();
		a.setId(1L);

		Code c = new Code();
		c.setEvent("start");
		c.setScript("var aw = ARENA_WIDTH; var ah = ARENA_HEIGHT; var ra = RADAR_ANGLE; var rr = RADAR_RADIUS; var lw = LUCHADOR_WIDTH; var lh = LUCHADOR_HEIGHT;");
		List<Code> codes = new ArrayList<Code>();
		codes.add(c);

		a.getCodePackage().setCodes(codes);

		match.add(a);

		while (match.getRunners().size() < 1) {
			logger.debug("esperando lutchadores se preparem para o combate");
			Thread.sleep(200);
		}

		LuchadorRunner runnerA = match.getRunners().get(new Long(1L));

		logger.debug("--- A : " + runnerA.getState().getPublicState());

		// start the match
		Thread t = new Thread(match);
		t.start();

		// stop the match
		Thread.sleep(500);
		match.kill();
		Thread.sleep(500);

		c.setScript("var aw = ARENA_WIDTH; var ah = ARENA_HEIGHT; var ra = RADAR_ANGLE; var rr = RADAR_RADIUS; var lw = LUCHADOR_WIDTH; var lh = LUCHADOR_HEIGHT;");

		logger.debug("--- arena width = "+ runnerA.getFromJavascript("aw") );
		
		assertEquals(match.getGameDefinition().getArenaWidth().toString(),
				runnerA.getFromJavascript("aw"));
		assertEquals(match.getGameDefinition().getArenaHeight().toString(),
				runnerA.getFromJavascript("ah"));
		assertEquals(match.getGameDefinition().getRadarAngle().toString(),
				runnerA.getFromJavascript("ra"));
		assertEquals(match.getGameDefinition().getRadarRadius().toString(),
				runnerA.getFromJavascript("rr"));
		assertEquals(Integer.toString(runnerA.getSize()),
				runnerA.getFromJavascript("lw"));
		assertEquals(Integer.toString(runnerA.getSize()),
				runnerA.getFromJavascript("lh"));

	}
}
