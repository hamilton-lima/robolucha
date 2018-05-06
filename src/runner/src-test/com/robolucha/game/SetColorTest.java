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
 * @author hamiltonlima
 */
public class SetColorTest {

    private static Logger logger = Logger.getLogger(SetColorTest.class);

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void testRun() throws Exception {

        MatchRunner match = MockMatchRunner.build();
        match.getGameDefinition().setMinParticipants(1);

        match.add(createLuchador(1L, "#FF0000", "#0000FF"));
        match.add(createLuchador(2L, "#F00", "#00F"));
        match.add(createLuchador(3L, "", ""));
        match.add(createLuchador(4L, "#", "#"));
        match.add(createLuchador(5L, "#FFFFFFFFFF", "#FFFFFFFFFFFFFFFFFF"));
        match.add(createLuchadorNMS(6L, "NMSColor.DRAGON_FRUIT", "NMSColor.OKRA"));

        while (match.getRunners().size() < 6) {
            logger.debug("esperando lutchadores se preparem para o combate : prontos=" + match.getRunners().size());
            logger.debug("lutchadores : " + match.getRunners());
            Thread.sleep(300);
        }

        LuchadorRunner runner1 = match.getRunners().get(new Long(1L));
        LuchadorRunner runner2 = match.getRunners().get(new Long(2L));
        LuchadorRunner runner3 = match.getRunners().get(new Long(3L));
        LuchadorRunner runner4 = match.getRunners().get(new Long(4L));
        LuchadorRunner runner5 = match.getRunners().get(new Long(5L));
        LuchadorRunner runner6 = match.getRunners().get(new Long(6L));

        logger.debug("--- 1 : " + runner1.getState().getPublicState());
        logger.debug("--- 2 : " + runner2.getState().getPublicState());
        logger.debug("--- 3 : " + runner3.getState().getPublicState());
        logger.debug("--- 4 : " + runner4.getState().getPublicState());
        logger.debug("--- 5 : " + runner5.getState().getPublicState());
        logger.debug("--- 6 : " + runner6.getState().getPublicState());

        // start the match
        Thread t = new Thread(match);
        t.start();

        // stop the match
        Thread.sleep(500);
        match.kill();
        Thread.sleep(500);

        logger.debug("--- 1 : " + runner1.getState().getPublicState());
        logger.debug("--- 2 : " + runner2.getState().getPublicState());
        logger.debug("--- 3 : " + runner3.getState().getPublicState());
        logger.debug("--- 4 : " + runner4.getState().getPublicState());
        logger.debug("--- 5 : " + runner5.getState().getPublicState());
        logger.debug("--- 6 : " + runner6.getState().getPublicState());

        assertTrue("verifica se cor mudou corretamente",
                "#FF0000".equals(runner1.getState().getPublicState().headColor));

        assertTrue("verifica se cor mudou corretamente",
                "#0000FF".equals(runner1.getState().getPublicState().bodyColor));

        assertTrue("verifica se cor mudou corretamente", "#F00".equals(runner2.getState().getPublicState().headColor));

        assertTrue("verifica se cor mudou corretamente", "#00F".equals(runner2.getState().getPublicState().bodyColor));

        assertTrue("verifica se cor mudou corretamente", "".equals(runner3.getState().getPublicState().headColor));

        assertTrue("verifica se cor mudou corretamente", "".equals(runner3.getState().getPublicState().bodyColor));

        assertTrue("verifica se cor mudou corretamente", "#".equals(runner4.getState().getPublicState().headColor));

        assertTrue("verifica se cor mudou corretamente", "#".equals(runner4.getState().getPublicState().bodyColor));

        assertTrue("verifica se cor mudou corretamente",
                "#FFFFFF".equals(runner5.getState().getPublicState().headColor));

        assertTrue("verifica se cor mudou corretamente",
                "#FFFFFF".equals(runner5.getState().getPublicState().bodyColor));

        // NMSColor.DRAGON_FRUIT = '#C9A62E';
        // NMSColor.OKRA = '#5E7434';

        assertTrue("verifica se cor mudou corretamente",
                "#C9A62E".equals(runner6.getState().getPublicState().headColor));

        assertTrue("verifica se cor mudou corretamente",
                "#5E7434".equals(runner6.getState().getPublicState().bodyColor));
    }

    private Luchador createLuchador(long id, String headColor, String bodyColor) {
        Luchador a = MockLuchador.build(id, MethodNames.START,
                "setHeadColor('" + headColor + "');setBodyColor('" + bodyColor + "');");
        return a;
    }

    private Luchador createLuchadorNMS(long id, String headColor, String bodyColor) {
        Luchador a = MockLuchador.build(id, MethodNames.START,
                "setHeadColor(" + headColor + ");setBodyColor(" + bodyColor + ");");
        return a;
    }
}
