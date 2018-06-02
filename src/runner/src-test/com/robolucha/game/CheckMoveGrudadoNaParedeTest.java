package com.robolucha.game;

import com.robolucha.models.Code;
import com.robolucha.models.Luchador;
import com.robolucha.runner.MatchRunner;
import com.robolucha.runner.luchador.LuchadorRunner;
import com.robolucha.runner.luchador.MethodNames;
import com.robolucha.test.MockLuchador;
import com.robolucha.test.MockMatchRunner;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class CheckMoveGrudadoNaParedeTest {

    private static Logger logger = Logger
            .getLogger(CheckMoveGrudadoNaParedeTest.class);

    @Before
    public void setUp() throws Exception {

        logger.setLevel(Level.ALL);
    }

    @Test
    public void testRun() throws Exception {

        MatchRunner match = MockMatchRunner.build();
        match.getGameDefinition().setMinParticipants(1);

        Luchador a = MockLuchador.build(1L, MethodNames.REPEAT, "move(-100);");

        Code c1 = new Code();
        c1.setEvent(MethodNames.ON_HIT_WALL);
        c1.setScript("turn(45);");
        a.getCodes().add(c1);

        match.add(a);

        match.getMatchStart()
                .blockingSubscribe(onStart -> {
                    LuchadorRunner runnerA = match.getRunners().get(new Long(1L));

                    // quase grudado no limite superior do mapa
                    runnerA.getState().setX((runnerA.getSize() / 2) + 2);
                    runnerA.getState().setY((runnerA.getSize() / 2) + 2);

                    logger.debug("--- A : " + runnerA.getState().getPublicState());
                    double startAngle = runnerA.getState().getPublicState().angle;

                    // start the match
                    Thread t = new Thread(match);
                    t.start();

                    // stop the match
                    Thread.sleep(2500);
                    match.kill();
                    Thread.sleep(1500);

                    logger.debug("--- A depois : " + runnerA.getState().getPublicState());

                    double finalAngle = runnerA.getState().getPublicState().angle;

                    logger.debug(String.format("*** resultados : a[%s, %s]", startAngle,
                            finalAngle));

                    assertTrue("verifica se lutchador girou ao colidir com parede",
                            finalAngle > startAngle);


                });

    }
}
