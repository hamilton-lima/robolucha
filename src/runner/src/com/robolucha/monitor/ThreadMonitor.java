package com.robolucha.monitor;

import java.util.Iterator;
import java.util.LinkedHashMap;

import org.apache.log4j.Logger;

import com.robolucha.runner.MatchRunner;

public class ThreadMonitor  {

	public static final long SMALL_SLEEP = 5;
	public static final long MEDIUM_SLEEP = 60;

	static Logger logger = Logger.getLogger(ThreadMonitor.class);

	private static ThreadMonitor instance;
	LinkedHashMap<String, ThreadStatus> threads;

	public ThreadMonitor() {
		threads = new LinkedHashMap<String, ThreadStatus>();
		instance = this;
	}

	public void register(MatchRunner thread) {
		threads.put(thread.getThreadName(), thread);
	}

	public void remove(ThreadStatus thread) {
		threads.remove(thread.getThreadName());
	}

	public static ThreadMonitor getInstance() {
		if (instance == null) {
			instance = new ThreadMonitor();
		}
		return instance;
	}

	/**
	 * //TODO trocar para retornar lista
	 */
	public MatchRunner getMatch() {

		Iterator iterator = threads.keySet().iterator();
		while (iterator.hasNext()) {
			Object key = (Object) iterator.next();
			Object one = threads.get(key);

			if (one instanceof MatchRunner) {
				MatchRunner runner = (MatchRunner) one;

				// para retornar a atual assim evita o bug de transicao entre
				// partidas
				if (((MatchRunner) one).isAlive()) {
					return (MatchRunner) one;
				}
			}
		}

		return null;
	}

	/**
	 * retorna o cenario atual dos threads em execucao
	 * 
	 * @return
	 */
	public ThreadStatusVO[] getStatus() {

		ThreadStatusVO[] result = new ThreadStatusVO[threads.size()];
		ThreadStatus one;
		int pos = 0;

		Iterator iterator = threads.keySet().iterator();
		while (iterator.hasNext()) {
			Object key = (Object) iterator.next();
			one = threads.get(key);
			result[pos] = new ThreadStatusVO(one.getThreadName(), one.getThreadStatus(), one.getThreadStartTime());
			pos++;
		}

		return result;
	}

	private static long counter = 0;

	public static String getUID() {
		counter++;
		return Long.toString(counter);
	}

	// TODO adicionar ao objeto ThreadStatus e controlar ack dos erros
	public void addException(String threadName, MessageList messageList) {
		logger.error("addException, thread=" + threadName + " errors=" + messageList);
	}

	// TODO: add listener for the APP destroy
	@Override
	public void contextDestroyed() {
		logger.debug("--- APP is exiting, time to shutdown all the threads.");

		Iterator iterator = threads.entrySet().iterator();
		while (iterator.hasNext()) {

			Object key = (Object) iterator.next();
			ThreadStatus one = threads.get(key);

			try {
				if (one != null) {
					one.kill();
				}
			} catch (Exception e) {
				logger.error("--- THREAD : " + one.getThreadName() + " is a highlander...", e);
			}

		}

	}


	public void alive(String threadName) {
		ThreadStatus thread = threads.get(threadName);
		if (thread != null) {
			thread.setLastAlive(System.currentTimeMillis());
		} else {
			if (logger.isDebugEnabled()) {
				logger.debug("tentativa de atualizar ultimo alive de thread nao encontrado : " + threadName);
			}
		}
	}

}