package com.robolucha.test;

import com.robolucha.game.vo.MatchRunStateVO;
import com.robolucha.models.GameDefinition;
import com.robolucha.models.Match;
import com.robolucha.publisher.MatchStatePublisher;
import com.robolucha.publisher.RemoteQueue;
import com.robolucha.runner.Config;
import com.robolucha.runner.MatchRunner;

public class MockMatchRunner {

    static class MatchStatePublisherSilent extends MatchStatePublisher {
        public MatchStatePublisherSilent() {
            super(new RemoteQueue(Config.getInstance()));
        }

        @Override
        public void update(MatchRunner matchRunner) throws Exception {
        }

        @Override
        public void publish(MatchRunStateVO vo) {
        }

    }

    public static MatchRunner build() {
        GameDefinition gameDefinition = new GameDefinition();

        Match match = new Match();
        MatchRunner runner = new MatchRunner(gameDefinition, match);

        runner.setPublisher(new MatchStatePublisherSilent());
        return runner;
    }

    public static void start(MatchRunner runner) {
        Thread t = new Thread(runner);
        t.start();
        runner.getOnMatchStart().blockingFirst();
    }

}
