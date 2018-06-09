package com.robolucha.publisher;

import com.robolucha.game.vo.MatchRunStateVO;
import com.robolucha.runner.MatchRunner;
import com.robolucha.test.MockMatchRunner;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;

public class MatchStatePublisherTest {

    MockRemoteQueue queue = new MockRemoteQueue();
    MatchStatePublisher publisher;

    @Before
    public void setup() {
        publisher = new MatchStatePublisher(queue);
    }

    @Test
    public void update() throws Exception {
        MatchRunner runner = MockMatchRunner.build();
        MatchRunStateVO vo = new MatchRunStateVO();
        long expected = runner.getGameDefinition().getDuration() - runner.getTimeElapsed();
        publisher.update(runner);
        MatchRunStateVO sentToQueue = (MatchRunStateVO) queue.lastPublished;
        assertEquals(expected, sentToQueue.clock);
    }

    @Test
    public void publish() throws Exception {
        MatchRunner runner = MockMatchRunner.build();
        MatchRunStateVO vo = new MatchRunStateVO();
        vo.clock = 42;
        long expected = 42;

        publisher.publish(vo);
        MatchRunStateVO sentToQueue = (MatchRunStateVO) queue.lastPublished;
        assertEquals(expected, sentToQueue.clock);
    }
}