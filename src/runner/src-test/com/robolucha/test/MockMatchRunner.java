package com.robolucha.test;

import com.robolucha.game.vo.MatchRunStateVO;
import com.robolucha.models.GameDefinition;
import com.robolucha.models.Match;
import com.robolucha.publisher.MatchStatePublisher;
import com.robolucha.runner.MatchRunner;
import com.robolucha.runner.RunAfterThisTask;

public class MockMatchRunner {

    static class MatchStatePublisherSilent extends MatchStatePublisher {
        public MatchStatePublisherSilent() {
        }

        @Override
        public void update(MatchRunner matchRunner) throws Exception {
        }

        @Override
        public void publish(MatchRunStateVO vo) {
        }

        @Override
        public void start(MatchRunner matchRunner) {
        }

        @Override
        public void end(MatchRunner matchRunner, RunAfterThisTask... runAfterThis) {
        }
    }

    public static MatchRunner build() {
        GameDefinition gameDefinition = new GameDefinition();

        Match match = new Match();
        MatchRunner runner = new MatchRunner(gameDefinition, match);

        runner.setPublisher(new MatchStatePublisherSilent());
        return runner;
    }

    public static Thread start(MatchRunner runner) {
        Thread t = new Thread(runner);
        t.start();
        return t;
    }

}
