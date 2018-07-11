package com.robolucha.score;

import com.robolucha.models.LuchadorMatchState;
import com.robolucha.runner.MatchRunner;
import com.robolucha.test.MockMatchRunner;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;

public class ScoreUpdaterTest {

    MatchRunner runner;
    ScoreUpdater scoreUpdater;
    LuchadorMatchState luchador1;
    LuchadorMatchState luchador2;

    @Before
    public void before() {
        runner = MockMatchRunner.build();
        scoreUpdater = new ScoreUpdater();
        luchador1 = new LuchadorMatchState();
        luchador2 = new LuchadorMatchState();
    }

    @Test
    public void ZeroStart() {
        assertEquals(luchador1.score.getScore(), 0);
        assertEquals(luchador1.score.getK(), 0);
        assertEquals(luchador1.score.getD(), 0);
    }

    @Test
    public void onKill() {
        scoreUpdater.onKill(runner, luchador1, luchador2);
        assertEquals(luchador1.score.getScore(), ScoreBuilder.KILL_POINTS);
        assertEquals(luchador1.score.getK(), 1);
        assertEquals(luchador1.score.getD(), 0);

        assertEquals(luchador2.score.getScore(), 0);
        assertEquals(luchador2.score.getK(), 0);
        assertEquals(luchador2.score.getD(), 1);

        scoreUpdater.onKill(runner, luchador1, luchador2);
        assertEquals(luchador1.score.getScore(), ScoreBuilder.KILL_POINTS * 2);
        assertEquals(luchador1.score.getK(), 2);
        assertEquals(luchador1.score.getD(), 0);

        assertEquals(luchador2.score.getScore(), 0);
        assertEquals(luchador2.score.getK(), 0);
        assertEquals(luchador2.score.getD(), 2);

        scoreUpdater.onKill(runner, luchador2, luchador1);
        assertEquals(luchador1.score.getScore(), ScoreBuilder.KILL_POINTS * 2);
        assertEquals(luchador1.score.getK(), 2);
        assertEquals(luchador1.score.getD(), 1);

        assertEquals(luchador2.score.getScore(), ScoreBuilder.KILL_POINTS);
        assertEquals(luchador2.score.getK(), 1);
        assertEquals(luchador2.score.getD(), 2);
    }

    @Test
    public void onDamage() {
        scoreUpdater.onDamage(runner, luchador1, luchador2, 1.0);
        scoreUpdater.onDamage(runner, luchador2, luchador1, 1.0);
        scoreUpdater.onDamage(runner, luchador2, luchador1, 1.0);
        scoreUpdater.onDamage(runner, luchador2, luchador1, 2.0);

        assertEquals(luchador1.score.getScore(), ScoreBuilder.DAMAGE_POINTS);
        assertEquals(0, luchador1.score.getK());
        assertEquals(0, luchador1.score.getD());

        assertEquals(ScoreBuilder.DAMAGE_POINTS * 4, luchador2.score.getScore() );
        assertEquals(0, luchador2.score.getK());
        assertEquals(0, luchador2.score.getD());
    }
}