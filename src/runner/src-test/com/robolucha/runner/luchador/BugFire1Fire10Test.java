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
 * @author hamiltonlima
 * @raquel - quando usando fire(1) no repeat nao consegue disparar um fire(10)
 * usando onfound
 */
public class BugFire1Fire10Test {

    private static Logger logger = Logger.getLogger(BugFire1Fire10Test.class);

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void testRun() throws Exception {

        MatchRunner match = MockMatchRunner.build();
        match.getGameDefinition().setMinParticipants(1);

        Luchador a = MockLuchador.build(1L, MethodNames.REPEAT, "fire(1);");
        a.getCodes().add(new Code(MethodNames.START, "var foundIt = 0;"));
        a.getCodes().add(new Code(MethodNames.ON_FOUND, "foundIt = 1; fire(10);"));

        match.add(a);

        match.add(MockLuchador.build(2L, MethodNames.REPEAT, "turn(20);"));

        match.getMatchStart()
                .blockingSubscribe(onStart -> {
                    LuchadorRunner runnerA = match.getRunners().get(new Long(1L));
                    LuchadorRunner runnerB = match.getRunners().get(new Long(2L));

                    runnerA.getState().setX(100);
                    runnerA.getState().setY(100);

                    runnerB.getState().setX(200);
                    runnerB.getState().setY(100);

                    logger.debug("--- A : " + runnerA.getState().getPublicState());
                    logger.debug("--- B : " + runnerB.getState().getPublicState());

                    // start the match
                    Thread t = new Thread(match);
                    t.start();

                    // stop the match
                    Thread.sleep(3500);
                    match.kill();
                    Thread.sleep(500);

                    logger.debug("--- A depois : " + runnerA.getState().getPublicState());
                    logger.debug("--- B depois : " + runnerB.getState().getPublicState());

                    String foundIt = runnerA.getString("foundIt");
                    logger.debug("*** foundIt = " + foundIt);

                    assertTrue("found foi executado ? ", foundIt.equals("1.0"));
                    assertTrue("B recebeu o disparo de 10 ?", runnerB.getState()
                            .getPublicState().life < 10);

                });

    }


}
