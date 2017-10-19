package com.robolucha.runner;

import org.apache.log4j.Logger;

import com.robolucha.event.MatchEvent;
import com.robolucha.models.LuchadorMatchState;

/**
 * 
 * @author hamiltonlima
 */
public class MatchEventHandler {
	static Logger logger = Logger.getLogger(MatchEventHandler.class);

	MatchRunner runner;
	private Thread thread;
	private MatchEventHandlerThread handlerThread;

	public MatchEventHandler(MatchRunner runner, String ownerThreadName) {
		this.runner = runner;
		logger.info("match event handler started : ");

		handlerThread = new MatchEventHandlerThread(this, "MatchEventHandler-" + ownerThreadName);
		thread = new Thread(handlerThread);
		thread.start();
	}

	public void cleanup() {
		logger.info("CLEAN-UP MatchEventHandler !!");

		handlerThread.kill();
		
		// no interrupt do ur cleaning
		//thread.interrupt();

		handlerThread = null;
		thread = null;
	}

	public void add(MatchEvent event) {
		logger.info("event add: " + event);
		handlerThread.add(event);
		// handlerThread.wakeup();
	}

	public void init(RunAfterThisTask... runAfterThis) {
		MatchEvent event = new MatchEvent(MatchEvent.ACTION_INIT, runAfterThis);
		add(event);
	}

	public void start(RunAfterThisTask... runAfterThis) {
		MatchEvent event = new MatchEvent(MatchEvent.ACTION_START, runAfterThis);
		add(event);
	}

	public void alive(RunAfterThisTask... runAfterThis) {
		MatchEvent event = new MatchEvent(MatchEvent.ACTION_ALIVE, runAfterThis);
		add(event);
	}

	public void end(RunAfterThisTask... runAfterThis) {
		MatchEvent event = new MatchEvent(MatchEvent.ACTION_END, runAfterThis);
		add(event);
	}

	public void damage(LuchadorMatchState lutchadorA, LuchadorMatchState lutchadorB, double amount,
			RunAfterThisTask... runAfterThis) {
		MatchEvent event = new MatchEvent(MatchEvent.ACTION_DAMAGE, lutchadorA, lutchadorB, amount, runAfterThis);
		add(event);
	}

	public void kill(LuchadorMatchState lutchadorA, LuchadorMatchState lutchadorB, RunAfterThisTask... runAfterThis) {
		MatchEvent event = new MatchEvent(MatchEvent.ACTION_KILL, lutchadorA, lutchadorB, runAfterThis);
		add(event);
	}

}