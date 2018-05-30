package com.robolucha.game;

import com.robolucha.models.Luchador;
import com.robolucha.models.LuchadorPublicState;
import com.robolucha.runner.MatchRunner;
import com.robolucha.runner.luchador.LuchadorRunner;
import com.robolucha.runner.luchador.MethodNames;
import com.robolucha.test.MockLuchador;
import com.robolucha.test.MockMatchRunner;
import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class CheckTurnJuntoComTurnGunTest {

    private static Logger logger = Logger
            .getLogger(CheckTurnJuntoComTurnGunTest.class);

    @Before
    public void setUp() throws Exception {

    }

    @Test
    public void testRun() throws Exception {

        MatchRunner match = MockMatchRunner.build();
        match.getGameDefinition().setMinParticipants(1);
        Luchador a = MockLuchador.build(1L, MethodNames.REPEAT, "move(10);turn(45);turnGun(-45);");

        match.add(a);

        match.getMatchStart()
                .subscribe(onStart -> {
                    LuchadorRunner runnerA = match.getRunners().get(new Long(1L));

                    // quase grudado no limite superior do mapa
                    runnerA.getState().setX((runnerA.getSize() / 2) + 2);
                    runnerA.getState().setY((runnerA.getSize() / 2) + 2);
                    runnerA.getState().setGunAngle(300);

                    logger.debug("--- A : " + runnerA.getState().getPublicState());
                    LuchadorPublicState start = (LuchadorPublicState) runnerA.getState().getPublicState();

                    // start the match
                    Thread t = new Thread(match);
                    t.start();

                    // stop the match
                    Thread.sleep(1500);
                    match.kill();
                    Thread.sleep(1500);

                    logger.debug("--- A depois : " + runnerA.getState().getPublicState());
                    LuchadorPublicState end = (LuchadorPublicState) runnerA.getState().getPublicState();

                    logger.debug(String.format("*** resultados angle : a[%s, %s]",
                            start.angle, end.angle));

                    logger.debug(String.format("*** resultados gun angle : a[%s, %s]",
                            start.gunAngle, end.gunAngle));

                    assertTrue("verifica se lutchador girou ", end.angle > start.angle);

                    assertTrue("verifica se lutchador girou arma ",
                            end.gunAngle < start.gunAngle);

                });


    }
}
