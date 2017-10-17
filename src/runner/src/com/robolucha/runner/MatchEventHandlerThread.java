package com.robolucha.runner;

import java.util.LinkedList;
import java.util.Queue;

import org.apache.log4j.Logger;

import com.robolucha.event.MatchEvent;
import com.robolucha.game.event.MatchEventListener;

public class MatchEventHandlerThread implements Runnable {

	private static final long SLEEP = 5;

	static Logger logger = Logger.getLogger(MatchEventHandlerThread.class);

	private MatchEventHandler matchEventHandler;
	private Queue<MatchEvent> events;
	private String name;
	private boolean alive;
	private Object[] eventListeners;

	public MatchEventHandlerThread(MatchEventHandler matchEventHandler, String name) {
		this.matchEventHandler = matchEventHandler;
		this.events = new LinkedList<MatchEvent>();
		this.name = name;
		this.alive = true;
	}

	// processa os ultimos eventos.
	public void kill() {
		logger.info("KILL MatchEventHandlerThread !!");
		this.alive = false;
	}

	public void add(MatchEvent event) {
		logger.info("evento adicionado : " + event);

		if (this.alive) {
			this.events.add(event);
		} else {
			throw new RuntimeException("processador de eventos esta encerrado (" + name + ")");
		}
	}

	// public void wakeup() {
	// synchronized (this) {
	// MatchEventHandler.logger.info("WAKE UP !!!");
	// this.notify();
	// }
	// }
	//
	public void run() {
		MatchEventHandler.logger.info("START MatchEventHandlerThread : " + name);
		Thread.currentThread().setName(name);

		String message = "== MatchEventHandler ocupado " + name;

		long logStart = System.currentTimeMillis();
		long logThreshold = 10000;

		while (alive | this.events.size() > 0) {

			MatchEvent event = this.events.poll();

			if (event != null) {
				MatchEventHandler.logger.info("--- novo evento : " + event);
				consume(event);
			}

			// try {
			// synchronized (this) {
			// MatchEventHandler.logger.info("zzzzzzzz");
			// this.wait();
			// }
			// } catch (InterruptedException e) {
			// MatchEventHandler.logger.info("erro esperando com o thread", e);
			// }

			if ((System.currentTimeMillis() - logStart) > logThreshold) {
				logStart = System.currentTimeMillis();
				logger.info(message);
			}

			try {
				Thread.sleep(SLEEP);
			} catch (InterruptedException e) {
				logger.error("interromperam este rapaz ocupado MatchEventHandlerThread");
			}
		}
		
		cleanup();

		MatchEventHandler.logger.info("END MatchEventHandlerThread : " + name);
	}

	private void cleanup() {
		this.matchEventHandler = null;
		this.events.clear();
		this.name = null;
		this.eventListeners = null;
	}

	private void consume(MatchEvent event) {
		this.eventListeners = this.matchEventHandler.runner.getMatchEventListeners().toArray();
		logger.info("START consumindo evento : " + event);
		logger.info("== listeners : " + eventListeners.length);

		for (int i = 0; i < eventListeners.length; i++) {
			MatchEventListener listener = (MatchEventListener) eventListeners[i];

			if (event.getAction() == MatchEvent.ACTION_DAMAGE) {
				listener.onDamage(this.matchEventHandler.runner, event.getLutchadorA(), event.getLutchadorB(),
						event.getAmount());
			}

			if (event.getAction() == MatchEvent.ACTION_KILL) {
				listener.onKill(this.matchEventHandler.runner, event.getLutchadorA(), event.getLutchadorB());
			}

			if (event.getAction() == MatchEvent.ACTION_INIT) {
				listener.onInit(this.matchEventHandler.runner);
			}

			if (event.getAction() == MatchEvent.ACTION_START) {
				listener.onStart(this.matchEventHandler.runner);
			}

			if (event.getAction() == MatchEvent.ACTION_END) {
				listener.onEnd(this.matchEventHandler.runner);
			}

			if (event.getAction() == MatchEvent.ACTION_ALIVE) {
				listener.onAlive(this.matchEventHandler.runner);
			}
		}

		if (event.getRunAfterThis() != null) {
			for (int i = 0; i < event.getRunAfterThis().length; i++) {
				event.getRunAfterThis()[i].run();
			}
		}

		logger.info("END consumindo evento : " + event);
	}

}