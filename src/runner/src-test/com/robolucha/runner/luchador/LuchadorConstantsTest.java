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
        String script = "var aw = ARENA_WIDTH; " +
                "var ah = ARENA_HEIGHT; " +
                "var ra = RADAR_ANGLE; " +
                "var rr = RADAR_RADIUS; " +
                "var lw = LUCHADOR_WIDTH; " +
                "var lh = LUCHADOR_HEIGHT;";

        Luchador a = MockLuchador.build(1L, MethodNames.REPEAT, script);

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
