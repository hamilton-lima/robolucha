package com.robolucha.game;

import com.robolucha.game.processor.RespawnProcessor;
import com.robolucha.models.Luchador;
import com.robolucha.runner.MatchRunner;
import com.robolucha.runner.luchador.LuchadorRunner;
import com.robolucha.test.MockLuchador;
import com.robolucha.test.MockMatchRunner;
import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class RespawnProcessorTest {

	private static Logger logger = Logger.getLogger(RespawnProcessorTest.class);

	@Before
	public void setup() {
	}

	@Test
	public void testCalculateLocations() throws Exception {

		MatchRunner match = MockMatchRunner.build();
		Luchador a = MockLuchador.build(1L);
		Luchador b = MockLuchador.build(2L);

		match.add(a);
		match.add(b);

		match.getMatchStart()
				.subscribe(onStart -> {
					LuchadorRunner runnerA = match.getRunners().get(new Long(1L));
					RespawnProcessor p = new RespawnProcessor(match);

					int size = runnerA.getSize();
					int w = match.getGameDefinition().getArenaWidth();
					int h = match.getGameDefinition().getArenaHeight();

					int border = size / 3;

					int lines = ((w - (2 * border)) / size) - 1;
					int columns = ((h - (2 * border)) / size) - 1;

					logger.debug("lines=" + lines);
					logger.debug("columns=" + columns);
					logger.debug("locations.length=" + p.getLocations().length);
					logger.debug("tamanho esperado=" + (lines * columns));

					assertTrue(p.getLocations().length == (lines * columns));

				});
	}

}
