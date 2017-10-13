package com.robolucha.runner;

import java.util.List;

import org.apache.log4j.Logger;

import com.robolucha.game.event.LuchadorEvent;
import com.robolucha.game.event.LuchadorEventListener;

/**
 * uma nova instancia eh criada para controlar cada evento ocorrido na
 * partida
 * 
 * @author hamiltonlima
 * 
 *         TODO: controlar numero de instancias para monitoramento do
 *         servidor
 */
class LuchadorEventHandler extends Thread {

	static Logger logger = Logger.getLogger(LuchadorEventHandler.class);
	
	private LuchadorEvent event;
	Object[] eventListeners;

	public LuchadorEventHandler(LuchadorEvent event,
			List<LuchadorEventListener> eventListeners) {
		this.event = event;
		this.eventListeners = eventListeners.toArray();

		logger.debug("lutchador event handler iniciado : " + event);
	}

	public void run() {
		 Thread.currentThread().setName("LuchadorEventHandler-Thread");

		for (int i = 0; i < eventListeners.length; i++) {
			LuchadorEventListener listener = (LuchadorEventListener) eventListeners[i];
			listener.listen(event);
		}
	}

}