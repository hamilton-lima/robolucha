package com.robolucha.publisher;

import com.robolucha.game.vo.*;
import com.robolucha.models.Bullet;
import com.robolucha.models.Luchador;
import com.robolucha.models.LuchadorPublicState;
import com.robolucha.models.MaskConfigVO;
import com.robolucha.runner.MatchRunner;
import com.robolucha.runner.Punch;
import com.robolucha.runner.RunAfterThisTask;
import com.robolucha.runner.luchador.LuchadorRunner;
import org.apache.log4j.Logger;

import java.util.*;

public class MatchStatePublisher {

    private static Logger logger = Logger.getLogger(MatchStatePublisher.class);

    private static MatchStatePublisher instance;
    private final RemoteQueue publisher;

    private Map<Long, MatchRunStateVO> matchStates;
    private Map<Long, MatchRunner> matchRunners;

    public MatchStatePublisher(RemoteQueue publisher) {
        matchStates = Collections.synchronizedMap(new HashMap<Long, MatchRunStateVO>());
        matchRunners = Collections.synchronizedMap(new HashMap<Long, MatchRunner>());
        this.publisher = publisher;
    }

    public void update(MatchRunner matchRunner) throws Exception {
        Long matchId = matchRunner.getMatch().getId();
        MatchRunStateVO vo = new MatchRunStateVO();

        Iterator<Long> iterator = matchRunner.getRunners().keySet().iterator();
        while (iterator.hasNext()) {
            Object key = (Object) iterator.next();
            LuchadorRunner runner = matchRunner.getRunners().get(key);

            // add score to the list
            if (runner != null) {
                vo.scores.add(runner.getScoreVO());

                if (runner.isActive()) {
                    // read data to update the state
                    Long lutchadorId = runner.getGameComponent().getId();
                    String name = runner.getGameComponent().getName();
                    LuchadorPublicState publicState = runner.getState().getPublicState();
                    MaskConfigVO mask = runner.getMask();

                    // luchador owner data
                    Long ownerId = null;
                    if (runner.getGameComponent() instanceof Luchador) {
                        Luchador luchador = (Luchador) runner.getGameComponent();
                        if (luchador.getCoach() != null) {
                            ownerId = luchador.getCoach().getId();
                        }
                    }

                    // update luchador data
                    vo.luchadores.add(new LuchadorPublicStateVO(publicState, name, ownerId, mask));
                }
            }

        }

        // TODO: move this to the data consumer.
        // sort based on the score
        Collections.sort(vo.scores);

        // bullets
        int pos = 0;
        while (pos < matchRunner.getBullets().size()) {
            Bullet bullet = (Bullet) matchRunner.getBullets().get(pos++);
            if (bullet == null) {
                continue;
            }

            BulletVO foo = new BulletVO();
            foo.id = bullet.getId();
            foo.x = bullet.getX();
            foo.y = bullet.getY();
            foo.amount = bullet.getAmount();

            vo.bullets.add(foo);
        }

        // punches
        pos = 0;
        while (pos < matchRunner.getPunches().size()) {
            Punch punch = (Punch) matchRunner.getPunches().get(pos++);
            if (punch == null) {
                continue;
            }

            PunchVO foo = new PunchVO();
            foo.x = punch.getX();
            foo.y = punch.getY();

            vo.punches.add(foo);
        }

        vo.clock = matchRunner.getGameDefinition().getDuration() - matchRunner.getTimeElapsed();

        publish(vo);

    }

    public void publish(MatchRunStateVO vo) {
        //TODO: MUST DO - send updates to REDIS
    }


    public void end(MatchRunner matchRunner, RunAfterThisTask... runAfterThis) {
        logger.debug("END " + matchRunner);

        if (runAfterThis != null) {
            for (int i = 0; i < runAfterThis.length; i++) {
                runAfterThis[i].run();
            }
        }
    }

    /**
     * adiciona as mensagens ao MatchRunStateVO
     *
     * @param state
     * @param matchId
     * @param luchadorId
     * @return
     */
//    public MatchRunStateVO getMessages(MatchRunStateVO state, Long matchId, Long luchadorId) {
//
//        if (logger.isDebugEnabled()) {
//            logger.debug(String.format("getMessages() matchId=%s, luchadorId=%s", matchId, luchadorId));
//        }
//
//        state.messages = new ArrayList<MessageVO>();
//        MatchRunner matchRunner = matchRunners.get(matchId);
//        LuchadorRunner luchadorRunner = matchRunner.getRunner(luchadorId);
//
//        if (logger.isDebugEnabled()) {
//            String matchRunnerId = matchRunner == null ? "NULL" : matchRunner.getMatch().getId().toString();
//            String luchadorRunnerId = luchadorRunner == null ? "NULL"
//                    : Long.toString(luchadorRunner.getGameComponent().getId());
//            logger.debug(String.format("matchrunner=%s, luchadorrunner=%s", matchRunnerId, luchadorRunnerId));
//        }
//
//        // luchador ainda nao consta na lista do MatchRunner
//        if (luchadorRunner != null) {
//            MessageVO message = luchadorRunner.getMessage();
//
//            if (logger.isDebugEnabled()) {
//                logger.debug(String.format("message=%s", message));
//            }
//
//            if (message != null) {
//                state.messages.add(message);
//            }
//        }
//
//        return state;
//    }

}
