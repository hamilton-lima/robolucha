package com.robolucha.runner;

import com.robolucha.event.match.MatchEventVO;
import com.robolucha.event.match.MatchEventVOStart;
import com.robolucha.game.action.*;
import com.robolucha.game.event.LuchadorEvent;
import com.robolucha.game.event.LuchadorEventListener;
import com.robolucha.game.event.MatchEventListener;
import com.robolucha.game.event.OnHitOtherEvent;
import com.robolucha.game.processor.BulletsProcessor;
import com.robolucha.game.processor.PunchesProcessor;
import com.robolucha.game.processor.RespawnProcessor;
import com.robolucha.game.vo.MessageVO;
import com.robolucha.models.Bullet;
import com.robolucha.models.GameComponent;
import com.robolucha.models.GameDefinition;
import com.robolucha.models.Match;
import com.robolucha.monitor.ThreadMonitor;
import com.robolucha.monitor.ThreadStatus;
import com.robolucha.publisher.MatchStatePublisher;
import com.robolucha.runner.luchador.LuchadorRunner;
import com.robolucha.runner.luchador.LutchadorRunnerCreator;
import io.reactivex.subjects.PublishSubject;
import org.apache.log4j.Logger;

import java.util.*;

/**
 * main match logic
 */
public class MatchRunner implements Runnable, ThreadStatus {

    private static final long SMALL_SLEEP = 5;
    private SafeList bullets;
    private SafeList punches;

    private List<GameAction> runOnActive;
    private PunchesProcessor punchesProcessor;
    private BulletsProcessor bulletsProcessor;
    private double delta;

    //TODO: replace all the listeners by Subjects
    private PublishSubject<MatchEventVO> onMatchStart;

    private PublishSubject<MessageVO> onMessage;

    private List<MatchRunnerListener> listeners;

    private GameDefinition gameDefinition;

    private MatchStatePublisher publisher;

    private List<LuchadorEventListener> eventListeners;
    private List<MatchEventListener> matchEventListeners;

    static Logger logger = Logger.getLogger(MatchRunner.class);

    LinkedHashMap<Long, LuchadorRunner> runners;
    boolean alive;
    private RespawnProcessor respawnProcessor;

    private String status;
    private String threadName;
    private Long startTime;
    // private MatchRun match;
    private long timeElapsed;
    private boolean cleanupActive = true;

    private MatchEventHandler eventHandler;
    private LutchadorRunnerCreator luchadorCreator;
    private Match match;

    public MatchEventHandler getEventHandler() {
        return eventHandler;
    }

    public MatchRunner(GameDefinition gamedefinition, Match match) {
        threadName = this.getClass().getName() + "-" + ThreadMonitor.getUID();

        status = ThreadStatus.STARTING;
        alive = true;
        delta = 0.0;
        this.gameDefinition = gamedefinition;

        this.match = match;

        listeners = new LinkedList<MatchRunnerListener>();

        runOnActive = new LinkedList<GameAction>();
        runOnActive.add(new RepeatAction());
        runOnActive.add(new CheckRadarAction(this));
        runOnActive.add(new TriggerEventsAction());
        runOnActive.add(new ChangeStateAction());

        runners = new LinkedHashMap<Long, LuchadorRunner>();

        respawnProcessor = new RespawnProcessor(this);

        bullets = new SafeList(new LinkedList<Bullet>());
        punches = new SafeList(new LinkedList<Punch>());

        eventListeners = Collections.synchronizedList(new LinkedList<LuchadorEventListener>());
        matchEventListeners = Collections.synchronizedList(new LinkedList<MatchEventListener>());

        punchesProcessor = new PunchesProcessor(this, punches);
        bulletsProcessor = new BulletsProcessor(this, bullets);

        eventHandler = new MatchEventHandler(this, threadName);
        luchadorCreator = new LutchadorRunnerCreator(this);

        onMatchStart = PublishSubject.create();
        onMessage = PublishSubject.create();

        logger.info("MatchRunner created:" + this);
    }

    public Match getMatch() {
        return match;
    }

    public void addListener( MatchRunnerListener listener){
        listeners.add( listener );
        listener.subscribe(this);
    }

    public void add(final GameComponent component) throws Exception {

        if (runners.containsKey(component.getId())) {
            logger.info("trying to add luchador that is already in the match, id: " + component.getId());
            return;
        }

        if (runners.size() >= gameDefinition.getMaxParticipants()) {
            throw new Exception("trying to add luchador beyond the limit");
        }

        logger.info("new luchador added to the match: " + component);
        luchadorCreator.add(component);

    }

    public void addLuchador(final GameComponent component) throws Exception {

        // check if luchador is in another arena
        if (MatchRunnerValidationHelper.getInstance().currentMatchFromLuchador(component) != null) {
            throw new RuntimeException("luchador is already participating in another match");
        }

        add(component);
    }


    public void fire(Bullet bullet) {

        if (logger.isDebugEnabled()) {
            logger.debug("-- FIRE ! = " + bullet);
        }

        bullets.add(bullet);
    }

    public void punch(LuchadorRunner lutchadorRunner, double amount, double x, double y, double angle) {
        Punch punch = new Punch(lutchadorRunner, amount, x, y, angle);
        punches.add(punch);
    }

    public void run() {
        Thread.currentThread().setName("MatchRunner-Thread" + gameDefinition.getName());

        // TODO: add observable to the init procedures

        /*
         *
         *
         * // event handler para init getEventHandler().init(new RunAfterThisTask(this)
         * { // soh executa main loop depois que acabar o init(); public void run() {
         * ((MatchRunner) data).readyForMainLoop(); } });
         *
         * while (!readyForMainLoop) { try { Thread.sleep(SMALL_SLEEP); } catch
         * (InterruptedException e) { logger.error("interrompido esperando pelo init()",
         * e); } }
         */
        mainLoop();
    }

    public void mainLoop() {
        CheckRespawnAction respawnAction = new CheckRespawnAction(this);
        RemoveDeadAction removeDeadAction = new RemoveDeadAction(this);

        startTime = new Long(System.currentTimeMillis());

        logger.info("waiting for the minimum participants:" + gameDefinition.getMinParticipants());

        while (alive && runners.size() < gameDefinition.getMinParticipants()) {
            try {
                Thread.sleep(SMALL_SLEEP);
            } catch (InterruptedException e) {
                logger.error("Interrupted while waiting for participants", e);
            }
        }

        //TODO: reduce to one single event
        onMatchStart.onNext( new MatchEventVOStart());
        onMatchStart.onComplete();

        getEventHandler().start();

        long timeStart = System.currentTimeMillis();
        this.timeElapsed = 0;

        long logStart = 0;
        long logThreshold = 30000;

        long start = System.currentTimeMillis();
        long current = 0;
        long elapsed = 0;
        int expectedElapsed = 0;

        logger.info("starting");

        while (alive) {
            current = System.currentTimeMillis();
            elapsed = current - start;
            start = current;
            delta = elapsed / 1000.0;
            expectedElapsed = (int) ((1000 / gameDefinition.getFps()) - elapsed);

            try {
                // adjust the FPS

                // TODO: allow inspection of this value
                if (expectedElapsed > 0) {
                    Thread.sleep(expectedElapsed);
                }
            } catch (InterruptedException e) {
                logger.error("Main loop interrupted", e);
            }

            timeElapsed = System.currentTimeMillis() - timeStart;
            if (timeElapsed > gameDefinition.getDuration()) {
                logger.info("end of match time elapsed time:" + timeElapsed + " max:" + gameDefinition.getDuration());
                break;
            }

            // TODO: add scheduled end time for a match
            /*
             * if (match.getGame() != null) { Date endTime = match.getGame().getEndTime();
             * if (endTime != null) { Date now = DateUtil.nowSaoPaulo(); if
             * (endTime.before(now)) { logger.info("chegou a hora de terminar a partida");
             * break; } } }
             */

            if ((System.currentTimeMillis() - logStart) > logThreshold) {
                logStart = System.currentTimeMillis();
                logger.info("MatchRunner active: " + gameDefinition.getName() + " FPS: " + gameDefinition.getFps());

                // getEventHandler().alive();
            }

            try {
                if (logger.isDebugEnabled()) {
                    logger.debug("**** TICK ****");
                }

                runOnActive.stream().sequential().forEach(action -> runAllActive(action));

                bulletsProcessor.process();
                punchesProcessor.process();

                // trata os mortinhos da silva
                runAll(removeDeadAction);
                runAll(respawnAction);

                // atualiza tempos de cooldown
                runAll(ReduceCoolDownAction.getInstance());

                publisher.update(this);

            } catch (Throwable e) {
                logger.error("*** ERRO NO LOOP DO MATCHRUN", e);
            }

        }

        logger.info("matchrun shutdown (1)");
        // desliga tratador de eventos
        getEventHandler().end(new RunAfterThisTask(this) {
            public void run() {
                logger.info("matchrun shutdown (2)");
                ((MatchRunner) data).shutDownServices();
            }
        });

    }

    public void shutDownServices() {
        logger.info("matchrun shutdown (3)");

        eventHandler.cleanup();
        luchadorCreator.cleanup();
        cleanup();
    }

    public void cleanup() {
        logger.info("matchrun shutdown (6)");

        if (cleanupActive) {
            cleanupActions();
        } else {
            logger.info("cleanup is not active, running TESTS? (9)");
        }
    }

    private void cleanupActions() {
        logger.info("matchmonitor remove (7)");

        logger.info("matchrun shutdown (8) luchador runners cleanup");
        LuchadorRunner[] localRunners = new LuchadorRunner[runners.values().size()];
        localRunners = runners.values().toArray(localRunners);

        for (int i = 0; i < localRunners.length; i++) {
            LuchadorRunner runner = localRunners[i];
            runner.cleanup();
            // runners.put(runner.getGameComponent().getId(), null);
        }

        listeners.stream().forEach( disposable -> disposable.dispose() );

        // cleanup
        runners.clear();
        bullets.clear();
        punches.clear();
        punchesProcessor.cleanup();
        bulletsProcessor.cleanup();

        eventListeners.clear();
        matchEventListeners.clear();
        respawnProcessor.cleanup();
        listeners.clear();

        runners = null;
        bullets = null;
        punches = null;
        punchesProcessor = null;
        bulletsProcessor = null;

        eventListeners = null;
        matchEventListeners = null;
        respawnProcessor = null;
        listeners = null;

        gameDefinition = null;

        eventHandler = null;
        luchadorCreator = null;

        logger.info("matchrun shutdown (9)");

    }

    public void runAllActive(GameAction action) {

        LuchadorRunner runner = null;

        if (logger.isDebugEnabled()) {
            logger.debug("runallactive : runners.size() = " + runners.size());
        }

        LuchadorRunner[] localRunners = new LuchadorRunner[runners.values().size()];
        localRunners = runners.values().toArray(localRunners);

        for (int i = 0; i < localRunners.length; i++) {
            runner = localRunners[i];

            if (logger.isDebugEnabled()) {
                logger.debug("runallactive : runner = " + runner);
                logger.debug("runallactive : runner.isActive() = " + runner.isActive());
                logger.debug(action.getName() + "-" + runner.getState());
            }

            if (runner != null && runner.isActive()) {

                try {
                    action.run(runners, runner);
                } catch (Exception e) {
                    runner.deactivate(action.getName());
                    runner.onMessage(MessageVO.DANGER, action.getName(), e.getMessage());

                    logger.error("error executing action: " + action.getName() + " on luchador: "
                            + runner.getGameComponent().getId(), e);
                }
            }

        }

        localRunners = null;

    }

    // TODO: merge with runallactive
    void runAll(GameAction action) {

        LuchadorRunner runner = null;

        if (logger.isDebugEnabled()) {
            logger.debug("runallactive : runners.size() = " + runners.size());
        }

        LuchadorRunner[] localRunners = new LuchadorRunner[runners.values().size()];
        localRunners = runners.values().toArray(localRunners);

        for (int i = 0; i < localRunners.length; i++) {
            runner = localRunners[i];

            if (logger.isDebugEnabled()) {
                logger.debug("runallactive : runner = " + runner);
            }

            if (runner != null) {

                try {
                    action.run(runners, runner);

                } catch (Exception e) {
                    runner.deactivate(action.getName());
                    runner.onMessage(MessageVO.DANGER, action.getName(), e.getMessage());

                    logger.error("erro executando acao :" + action.getName() + " no lutchador : "
                            + runner.getGameComponent().getId(), e);
                }
            }

        }

        localRunners = null;

    }

    public double getDelta() {
        return delta;
    }

    public GameDefinition getGameDefinition() {
        return gameDefinition;
    }

    /**
     * verifica se pode mover para determinada posicao
     *
     * @param source
     * @param x
     * @param y
     * @return
     */
    public boolean canMove(LuchadorRunner source, double x, double y) {

        if (logger.isDebugEnabled()) {
            logger.debug(String.format("START canMove: [%s, %s] %s", x, y, source));
            logger.debug(String.format("runners size [%s]", runners.keySet().size()));
        }

        boolean result = true;

        Iterator iterator = runners.keySet().iterator();
        while (iterator.hasNext()) {
            Object key = (Object) iterator.next();
            LuchadorRunner target = runners.get(key);

            if (logger.isDebugEnabled()) {
                logger.debug(">> verificando colisao de :" + source.getGameComponent().getId() + " em x,y=" + x + ","
                        + y + " no lutchador : " + target.getGameComponent().getId());
            }

            if (target != null && target.isActive()
                    && source.getGameComponent().getId() != target.getGameComponent().getId()) {

                if (logger.isDebugEnabled()) {
                    boolean colide = Calc.intersectRobot(x, y, source, target);
                    logger.debug(">>> realizando teste da colisao com : " + target);
                    if (colide) {
                        logger.debug(">>> COLIDE ");
                    }
                }

                try {
                    // se colidiu com outros robos gera evento de colisao
                    // em cada um
                    //
                    if (Calc.intersectRobot(x, y, source, target)) {
                        source.addEvent(new OnHitOtherEvent(source.getState().getPublicState(),
                                target.getState().getPublicState()));

                        target.addEvent(new OnHitOtherEvent(source.getState().getPublicState(),
                                source.getState().getPublicState()));

                        if (logger.isDebugEnabled()) {
                            logger.debug("+++ ouch !");
                        }
                        result = false;
                    }

                } catch (Exception e) {
                    logger.debug("erro verificando colisao de :" + source.getGameComponent().getId() + "em x,y=" + x
                            + "," + y + " no lutchador : " + target.getGameComponent().getId());
                }
            }

        }

        return result;
    }

    public void addLuchadorEvent(LuchadorEvent event) {
        LuchadorEventHandler handler = new LuchadorEventHandler(event, eventListeners);
        handler.start();
    }

    public void addListener(LuchadorEventListener listener) {
        eventListeners.add(listener);
    }

    public void addListener(MatchEventListener listener) {
        matchEventListeners.add(listener);
    }

    public void listen(LuchadorRunner runner, String message) {
        runAllActive(new AddOnListenEventAction(runner.getState().getPublicState(), message));
    }

    public RespawnPoint getRespawnPoint(LuchadorRunner runner) {
        return respawnProcessor.getRespawnPoint(runner, runners);
    }

    public String getThreadStatus() {
        return status;
    }

    public String getThreadName() {
        return threadName;
    }

    public Long getThreadStartTime() {
        return startTime;
    }

    public List<MatchEventListener> getMatchEventListeners() {
        return matchEventListeners;
    }

    public SafeList getBullets() {
        return bullets;
    }

    public SafeList getPunches() {
        return punches;
    }

    public LinkedHashMap<Long, LuchadorRunner> getRunners() {
        return runners;
    }

    public LuchadorRunner getRunner(Long luchadorId) {
        if (runners == null) {
            return null;
        }
        return runners.get(luchadorId);
    }

    public boolean isAlive() {
        return alive;
    }

    public void kill() {
        alive = false;
    }

    public long getTimeElapsed() {
        return timeElapsed;
    }

    private long lastTimeAlive;

    @Override
    public void setLastAlive(long lastTimeAlive) {
        this.lastTimeAlive = lastTimeAlive;
    }

    @Override
    public long getLastAlive() {
        return lastTimeAlive;
    }

    public void setPublisher(MatchStatePublisher publisher) {
        this.publisher = publisher;
    }

    public PublishSubject<MatchEventVO> getOnMatchStart(){ return onMatchStart; }

    public PublishSubject<MessageVO> getOnMessage() {
        return onMessage;
    }
}
