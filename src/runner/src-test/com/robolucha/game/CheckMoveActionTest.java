package com.robolucha.game;

import com.robolucha.models.Luchador;
import com.robolucha.runner.MatchRunner;
import com.robolucha.runner.luchador.LuchadorRunner;
import com.robolucha.test.MockLuchador;
import com.robolucha.test.MockMatchRunner;
import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class CheckMoveActionTest {

    private static Logger logger = Logger.getLogger(CheckMoveActionTest.class);
    private static int counter = 0;

    @Before
    public void setUp() throws Exception {
        counter = 0;
    }

    @Test
    public void testRun() throws Exception {

        MatchRunner match = MockMatchRunner.build();

        Luchador a = MockLuchador.build(1L, "repeat", "move(10);");
        Luchador b = MockLuchador.build(2L, "repeat", "move(-10);");

        match.add(a);
        match.add(b);

        match.getMatchStart()
                .blockingSubscribe(onStart -> {
                    LuchadorRunner runnerA = match.getRunners().get(new Long(1L));
                    LuchadorRunner runnerB = match.getRunners().get(new Long(2L));

                    runnerA.getState().setX(100);
                    runnerA.getState().setY(100);

                    runnerB.getState().setX(100);
                    runnerB.getState().setY(250);

                    double aX1 = runnerA.getState().getPublicState().x;
                    double bX1 = runnerB.getState().getPublicState().x;

                    logger.debug("--- A : " + runnerA.getState().getPublicState());
                    logger.debug("--- B : " + runnerB.getState().getPublicState());

                    // start the match
                    Thread t = new Thread(match);
                    t.start();

                    // stop the match
                    Thread.sleep(1500);
                    match.kill();
                    Thread.sleep(500);

                    logger.debug("--- A depois : " + runnerA.getState().getPublicState());
                    logger.debug("--- B depois : " + runnerB.getState().getPublicState());

                    double aX2 = runnerA.getState().getPublicState().x;
                    double bX2 = runnerB.getState().getPublicState().x;

                    logger.debug(String.format("*** resultados : a[%s, %s], b[%s, %s]",
                            aX1, aX2, bX1, bX2));

                    assertTrue("verifica se lutchador moveu A", aX2 > aX1);
                    assertTrue("verifica se lutchador moveu B", bX2 < bX1);

                });


    }
}
