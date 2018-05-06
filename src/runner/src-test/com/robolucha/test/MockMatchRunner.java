package com.robolucha.test;

import com.robolucha.models.GameDefinition;
import com.robolucha.models.Match;
import com.robolucha.runner.MatchRunner;

public class MockMatchRunner {

    public static MatchRunner build(){
        GameDefinition gameDefinition = new GameDefinition();
        Match match = new Match();
        MatchRunner runner = new MatchRunner(gameDefinition, match);
        return runner;
    }
}
