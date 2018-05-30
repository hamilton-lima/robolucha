package com.robolucha.game;

import com.robolucha.models.Luchador;
import com.robolucha.runner.MatchRunner;
import com.robolucha.runner.luchador.LuchadorRunner;
import com.robolucha.runner.luchador.MethodNames;
import com.robolucha.test.MockLuchador;
import com.robolucha.test.MockMatchRunner;
import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

/**
 * atraves da variavel me com possibilidade de posicionar o lutchador arbitrariamente.
 *
 * @author hamiltonlima
 */
public class CheckFireCoolDownVariable {

    private static Logger logger = Logger.getLogger(CheckFireCoolDownVariable.class);

    @Before
    public void setUp() throws Exception {
    }


    @Test
    public void testRun() throws Exception {

        MatchRunner match = MockMatchRunner.build();
        match.getGameDefinition().setMinParticipants(1);

        Luchador a = MockLuchador.build(1L, MethodNames.REPEAT,
                "fire(3); if (me.fireCoolDown > 1) {move(10);}");

        match.add(a);

        match.getMatchStart()
                .subscribe(onStart -> {
                    LuchadorRunner runnerA = match.getRunners().get(new Long(1L));

                    runnerA.getState().setX(100);
                    runnerA.getState().setY(100);

                    logger.debug("--- A : " + runnerA.getState().getPublicState());
                    logger.debug("--- A : " + runnerA.getState());

                    // start the match
                    Thread t = new Thread(match);
                    t.start();

                    // stop the match
                    Thread.sleep(4000);
                    match.kill();
                    Thread.sleep(500);

                    logger.debug("--- A depois : " + runnerA.getState().getPublicState());
                    logger.debug("--- A depois : " + runnerA.getState());

                    assertTrue("verifica se lutchador ficou no lugar certo ...", runnerA
                            .getState().getX() > 100);


                });


    }
}
