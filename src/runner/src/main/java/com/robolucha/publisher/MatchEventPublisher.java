package com.robolucha.publisher;

import com.robolucha.event.ConstEvents;
import com.robolucha.game.event.MatchEventListener;
import com.robolucha.models.LuchadorMatchState;
import com.robolucha.models.Match;
import com.robolucha.models.MatchEvent;
import com.robolucha.models.MatchScore;
import com.robolucha.monitor.ThreadMonitor;
import com.robolucha.runner.MatchRunner;
import com.robolucha.runner.MatchRunnerAPI;
import com.robolucha.runner.luchador.LuchadorRunner;
import org.apache.log4j.Logger;

import java.util.Iterator;

public class MatchEventPublisher implements MatchEventListener {
    private final RemoteQueue publisher;
    private final ThreadMonitor threadMonitor;

    private Logger logger = Logger.getLogger(MatchEventPublisher.class);
    private Match match;

    public MatchEventPublisher(Match match, RemoteQueue publisher, ThreadMonitor threadMonitor) {
        this.match = match;
        this.publisher = publisher;
        this.threadMonitor = threadMonitor;
    }

    public void onInit(MatchRunner runner) {
        logger.debug("match INIT : " + runner.getThreadName());
        addEvent(runner, ConstEvents.INIT);
    }

    public void onStart(MatchRunner runner) {
        logger.debug("match START : " + runner.getThreadName());
        long timestamp = System.currentTimeMillis();
        match.setTimeStart(timestamp);
        match.setLastTimeAlive(timestamp);

        try {
            addEvent(runner, ConstEvents.START);
        } catch (Exception e) {
            reportErrors(runner, "ERROR, updating match:", e);
        }

    }

    public void onEnd(MatchRunner runner) {
        logger.debug("match END : " + runner.getThreadName());
        long timestamp = System.currentTimeMillis();
        match.setTimeEnd(timestamp);
        match.setLastTimeAlive(timestamp);

        try {
            MatchRunnerAPI.getInstance().updateMatch(match);
        } catch (Exception e) {
            reportErrors(runner, "ERROR, updating END of match:", e);
        }

        logger.info("Match END saving score");
        Iterator<Long> iterator = runner.getRunners().keySet().iterator();
        while (iterator.hasNext()) {
            Object key = (Object) iterator.next();
            LuchadorRunner luchadorRunner = runner.getRunners().get(key);
            saveFinalScore(runner, luchadorRunner);
        }

        addEvent(runner, ConstEvents.END);

    }

    private void saveFinalScore(MatchRunner runner, LuchadorRunner luchadorRunner) {

        if (logger.isInfoEnabled()) {
            logger.info("update score=" + luchadorRunner.getScoreVO());
        }

        try {
            MatchScore score = MatchRunnerAPI.getInstance().findScore(runner.getMatch(),
                    luchadorRunner.getGameComponent());

            if (score == null) {
                score = new MatchScore();
                score.setMatchRun(runner.getMatch());
                score.setGameComponent(luchadorRunner.getGameComponent());
                score.setKills(luchadorRunner.getScoreVO().getK());
                score.setDeaths(luchadorRunner.getScoreVO().getD());
                score.setScore(luchadorRunner.getScoreVO().getScore());
                MatchRunnerAPI.getInstance().addScore(score);
            } else {
                score.setKills(luchadorRunner.getScoreVO().getK());
                score.setDeaths(luchadorRunner.getScoreVO().getD());
                score.setScore(luchadorRunner.getScoreVO().getScore());

                MatchRunnerAPI.getInstance().updateScore(score);
            }
        } catch (Exception e) {
            reportErrors(runner, "ERROR, updating score:", e);

        }

    }

    private void reportErrors(MatchRunner runner, String message, Exception e) {
        logger.error(message, e);
        threadMonitor.addException(runner.getThreadName(), message);
    }

    public void onAlive(MatchRunner runner) {
        Match match = runner.getMatch();
        match.setLastTimeAlive(System.currentTimeMillis());

        try {
            threadMonitor.alive(runner.getThreadName());
        } catch (Exception e) {
            reportErrors(runner, "ERROR, updating match last alive time:", e);
        }

    }

    @Override
    public void onKill(MatchRunner runner, LuchadorMatchState luchadorA, LuchadorMatchState luchadorB) {

        logger.debug("match onKill : " + runner.getThreadName());
        addLuchadorEvent(runner, luchadorA, luchadorB, 0.0, ConstEvents.KILL);

    }

    @Override
    public void onDamage(MatchRunner runner, LuchadorMatchState luchadorA, LuchadorMatchState luchadorB,
                         Double amount) {

        logger.debug("match onDamage : " + runner.getThreadName());
        addLuchadorEvent(runner, luchadorA, luchadorB, amount, ConstEvents.DAMAGE);

    }

    public void addEvent(MatchRunner runner, String name) {
        addLuchadorEvent(runner, null, null, 0.0, name);
    }

    public void addLuchadorEvent(MatchRunner runner, LuchadorMatchState luchadorA, LuchadorMatchState luchadorB,
                                 Double amount, String name) {


        logger.debug("match addLuchadorEvent : " + runner.getThreadName());

        MatchEvent event = new MatchEvent();
        event.setMatch(runner.getMatch());
        event.setComponentA(luchadorA == null ? 0 : luchadorA.id);
        event.setComponentB(luchadorB == null ? 0 : luchadorB.id);
        event.setAmount(amount);
        event.setTimeStart(System.currentTimeMillis());
        event.setEvent(name);

        if (logger.isDebugEnabled()) {
            logger.debug("matchrunner.id=" + runner.getMatch().getId() + " " + name + " event=" + event);
        }

        publisher.publish(event);
    }

}
