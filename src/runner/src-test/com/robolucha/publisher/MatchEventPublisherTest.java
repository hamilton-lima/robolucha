package com.robolucha.publisher;

import com.robolucha.event.ConstEvents;
import com.robolucha.models.LuchadorMatchState;
import com.robolucha.models.Match;
import com.robolucha.models.MatchEvent;
import com.robolucha.monitor.ThreadMonitor;
import com.robolucha.runner.MatchRunner;
import com.robolucha.test.MockLuchador;
import com.robolucha.test.MockMatchRunner;
import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;

public class MatchEventPublisherTest {

    private static Logger logger = Logger.getLogger(MatchEventPublisherTest.class);

    private static class MockThreadMonitor extends ThreadMonitor {

        public String lastAlive;

        @Override
        public void alive(String threadName) {
            this.lastAlive = threadName;
        }
    }

    MatchEventPublisher publisher;
    MockThreadMonitor threadMonitor;
    MatchRunner runner;
    MockRemoteQueue queue;
    Long id = 42L;
    Long start;

    @Before
    public void setUp() throws Exception {
        queue = new MockRemoteQueue();
        threadMonitor = new MockThreadMonitor();

        publisher = new MatchEventPublisher(new Match(), queue, threadMonitor);
        runner = MockMatchRunner.build();
        runner.getMatch().setId(id);
        logger.debug("match " + runner.getMatch());
        start = System.currentTimeMillis();
    }

    @Test
    public void onInit() {
        publisher.onInit(runner);
        MatchEvent event = (MatchEvent) queue.lastPublished;
        logger.debug("last published " + event);

        assertTrue(event.getTimeStart() >= start);
        assertEquals(id, event.getMatch().getId());
        assertEquals(ConstEvents.INIT, event.getEvent());
        assertEquals(0L, event.getComponentA());
        assertEquals(0L, event.getComponentB());
        assertEquals(0.0, event.getAmount());
    }

    @Test
    public void onStart() {
        publisher.onStart(runner);
        MatchEvent event = (MatchEvent) queue.lastPublished;
        logger.debug("last published " + event);

        assertTrue(event.getTimeStart() >= start);
        assertEquals(id, event.getMatch().getId());
        assertEquals(ConstEvents.START, event.getEvent());
        assertEquals(0L, event.getComponentA());
        assertEquals(0L, event.getComponentB());
        assertEquals(0.0, event.getAmount());
    }

    @Test
    public void onEnd() {
        publisher.onEnd(runner);
        MatchEvent event = (MatchEvent) queue.lastPublished;
        logger.debug("last published " + event);

        assertTrue(event.getTimeStart() >= start);
        assertEquals(id, event.getMatch().getId());
        assertEquals(ConstEvents.END, event.getEvent());
        assertEquals(0L, event.getComponentA());
        assertEquals(0L, event.getComponentB());
        assertEquals(0.0, event.getAmount());
    }

    @Test
    public void onAlive() {
        publisher.onAlive(runner);
        assertTrue(runner.getMatch().getLastTimeAlive() >= start);
        assertEquals(runner.getThreadName(), threadMonitor.lastAlive);
    }

    @Test
    public void onKill() {
        LuchadorMatchState luchador1 = MockLuchador.buildLuchadorMatchState(1L);
        LuchadorMatchState luchador2 = MockLuchador.buildLuchadorMatchState(2L);

        publisher.onKill(runner, luchador1, luchador2);
        MatchEvent event = (MatchEvent) queue.lastPublished;
        logger.debug("last published " + event);

        assertTrue(event.getTimeStart() >= start);
        assertEquals(id, event.getMatch().getId());

        assertEquals(ConstEvents.KILL, event.getEvent());
        assertEquals(1L, event.getComponentA());
        assertEquals(2L, event.getComponentB());
        assertEquals(0.0, event.getAmount());
    }

    @Test
    public void onDamage() {
        LuchadorMatchState luchador1 = MockLuchador.buildLuchadorMatchState(1L);
        LuchadorMatchState luchador2 = MockLuchador.buildLuchadorMatchState(2L);

        publisher.onDamage(runner, luchador2, luchador1, 54.3);
        MatchEvent event = (MatchEvent) queue.lastPublished;
        logger.debug("last published " + event);

        assertTrue(event.getTimeStart() >= start);
        assertEquals(id, event.getMatch().getId());

        assertEquals(ConstEvents.DAMAGE, event.getEvent());
        assertEquals(2L, event.getComponentA());
        assertEquals(1L, event.getComponentB());
        assertEquals(54.3, event.getAmount());
    }
}