package com.robolucha.runner;

import com.robolucha.event.ConstEvents;
import com.robolucha.event.GeneralEventManager;
import com.robolucha.models.Code;
import com.robolucha.models.GameDefinition;
import com.robolucha.models.Luchador;
import com.robolucha.models.Match;
import com.robolucha.runner.luchador.LuchadorRunner;
import com.robolucha.test.MockLuchador;
import com.robolucha.test.MockMatchRunner;
import org.apache.log4j.Logger;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertFalse;


public class GeneralEventManagerTest {

    private static Logger logger = Logger
            .getLogger(GeneralEventManagerTest.class);

    @Test
    public void testChangeName() throws Exception {

        GameDefinition gameDefinition = Server.loadGameDefinition("default-gamedefinition.json");
        Match match = MatchRunnerAPI.getInstance().createMatch(gameDefinition);
        MatchRunner runner = new MatchRunner(gameDefinition, match);

        Luchador a = MockLuchador.build();
        a.setId(1L);
        a.setName("foo");

        Luchador b = MockLuchador.build();
        b.setId(2L);

        Code c = new Code();
        c.setEvent("onFound");
        c.setScript("// does nothing ... ");
        List<Code> codes = new ArrayList<Code>();
        codes.add(c);

        runner.add(a);
        runner.add(b);

        MockMatchRunner.start(runner);

        LuchadorRunner runnerA = runner.getRunners().get(new Long(1L));
        String name1 = runnerA.getScoreVO().getName();
        logger.debug("name1=" + name1);

        Luchador newGuy = MockLuchador.build();
        newGuy.setId(1L);
        newGuy.setName("foo.bar");

        GeneralEventManager.getInstance().handle(
                ConstEvents.LUCHADOR_NAME_CHANGE, newGuy);

        String name2 = runnerA.getScoreVO().getName();
        logger.debug("name2=" + name2);

        assertFalse(name1.equals(name2));

    }

}
