package com.robolucha.runner.luchador;

import com.robolucha.models.Code;
import com.robolucha.models.Luchador;
import com.robolucha.runner.MatchRunner;
import com.robolucha.test.MockLuchador;
import com.robolucha.test.MockMatchRunner;
import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

/**
 * onFound não está achando se estiver muito perto?
 *
 * @author rudnetto
 */
public class BugOnFoundPertoDemais {

    private static Logger logger = Logger
            .getLogger(BugOnFoundPertoDemais.class);

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void testRun() throws Exception {

        MatchRunner match = MockMatchRunner.build();
        match.getGameDefinition().setMinParticipants(1);

        Luchador a = MockLuchador.build(1L, MethodNames.ON_FOUND, "move(-10);");
        match.add(a);

        Luchador b = MockLuchador.build(2L);

        match.getMatchStart()
                .blockingSubscribe(onStart -> {
                    LuchadorRunner runnerA = match.getRunners().get(new Long(1L));

                    runnerA.getState().setX(200);
                    runnerA.getState().setY(100);
                    runnerA.getState().setAngle(90);
                    runnerA.getState().setGunAngle(90);

                    LuchadorRunner runnerB = match.getRunners().get(new Long(2L));

                    runnerB.getState().setX(200);
                    runnerB.getState().setY(299);
                    runnerB.getState().setAngle(0);
                    runnerB.getState().setGunAngle(0);

                    logger.debug("--- A : " + runnerA.getState().getPublicState());

                    // start the match
                    Thread t = new Thread(match);
                    t.start();

                    // stop the match
                    Thread.sleep(500);
                    match.kill();
                    Thread.sleep(500);

                    logger.debug("--- A depois : " + runnerA.getState().getPublicState());

                    assertTrue("verifica se o onFound do luchador A foi bem sucedido",
                            runnerA.getState().getPublicState().y < 100);
                });


    }

    @Test
    public void testRun2() throws Exception {
        testWithPosition(100, 100, 0, 200, 100);
        testWithPosition(100, 100, 45, 150, 150);
    }


    public void testWithPosition(int xa, int ya, int gunAngle, int xb, int yb) throws Exception {

        logger.debug(String.format("******* testWithposition, A(%s,%s) gunangle=%s -> B(%s,%s)", xa, ya, gunAngle, xb, yb));

        MatchRunner match = MockMatchRunner.build();
        match.getGameDefinition().setMinParticipants(1);

        Luchador a = MockLuchador.build(1L, MethodNames.START, "var found2 = 0;");

        Code c = new Code();
        c.setEvent(MethodNames.ON_FOUND);
        c.setScript("found2 = 1;");
        a.getCodes().add(c);

        match.add(a);

        Luchador b = MockLuchador.build(2L);
        match.add(b);

        match.getMatchStart()
                .blockingSubscribe(onStart -> {
                    LuchadorRunner runnerA = match.getRunners().get(new Long(1L));

                    runnerA.getState().setX(xa);
                    runnerA.getState().setY(ya);
                    runnerA.getState().setGunAngle(gunAngle);

                    LuchadorRunner runnerB = match.getRunners().get(new Long(2L));

                    runnerB.getState().setX(xb);
                    runnerB.getState().setY(yb);

                    logger.debug("--- A : " + runnerA.getState().getPublicState());

                    // start the match
                    Thread t = new Thread(match);
                    t.start();

                    // stop the match
                    Thread.sleep(500);
                    match.kill();
                    Thread.sleep(500);

                    logger.debug("--- A depois : " + runnerA.getState().getPublicState());
                    String found2 = runnerA.getString("found2");
                    logger.debug("--- A found2 : " + found2);

                    assertTrue("verifica se o onFound do luchador A foi bem sucedido",
                            found2.equals("1.0"));

                });


    }

}
