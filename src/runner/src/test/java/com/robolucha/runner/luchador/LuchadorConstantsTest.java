package com.robolucha.runner.luchador;

import com.robolucha.models.Luchador;
import com.robolucha.runner.MatchRunner;
import com.robolucha.test.MockLuchador;
import com.robolucha.test.MockMatchRunner;
import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class LuchadorConstantsTest {

    private static Logger logger = Logger
            .getLogger(LuchadorConstantsTest.class);

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void testRun() throws Exception {

        MatchRunner match = MockMatchRunner.build();
        String script = " aw = ARENA_WIDTH; " +
                " ah = ARENA_HEIGHT; " +
                " ra = RADAR_ANGLE; " +
                " rr = RADAR_RADIUS; " +
                " lw = LUCHADOR_WIDTH; " +
                " lh = LUCHADOR_HEIGHT;";

        Luchador a = MockLuchador.build(1L, MethodNames.REPEAT, script);

        match.add(a);
        match.add(MockLuchador.build());

        MockMatchRunner.start(match);

        LuchadorRunner runnerA = match.getRunners().get(new Long(1L));
        logger.debug("--- A : " + runnerA.getState().getPublicState());

        // stop the match
        Thread.sleep(500);
        match.kill();
        Thread.sleep(500);

        logger.debug("--- arena width = " + runnerA.getString("aw"));

        assertEquals(match.getGameDefinition().getArenaWidth(),
                runnerA.getString("aw"));
        assertEquals(match.getGameDefinition().getArenaHeight(),
                runnerA.getString("ah"));

        assertEquals(Integer.toString(runnerA.getSize()),
                runnerA.getString("lw"));
        assertEquals(Integer.toString(runnerA.getSize()),
                runnerA.getString("lh"));

    }
}
