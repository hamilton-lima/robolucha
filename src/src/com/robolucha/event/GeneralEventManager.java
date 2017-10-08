package com.robolucha.event;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import org.apache.log4j.Logger;

public class GeneralEventManager {

	private static Logger logger = Logger.getLogger(GeneralEventManager.class);

	private static GeneralEventManager instance = new GeneralEventManager();

	private HashMap<String, ArrayList<GeneralEventHandler>> handlers = new HashMap<String, ArrayList<GeneralEventHandler>>();

	private GeneralEventManager() {
	}

	public static GeneralEventManager getInstance() {
		return instance;
	}

	/**
	 * 
	 * @param event
	 * @param handler
	 */
	public void addHandler(String event, GeneralEventHandler handler) {
		if (logger.isDebugEnabled()) {
			logger.debug("add Handler event=" + event);
			logger.debug("add Handler handler=" + handler);
		}

		ArrayList<GeneralEventHandler> list = handlers.get(event);
		if (list == null) {
			list = new ArrayList<GeneralEventHandler>();
			handlers.put(event, list);

			if (logger.isDebugEnabled()) {
				logger.debug("new list");
			}

		}

		list.add(handler);
	}

	/**
	 * notify the event handlers 
	 * 
	 * @param event
	 * @param data
	 */
	public void handle(String event, Object data) {
		if (logger.isDebugEnabled()) {
			logger.debug("handle event=" + event);
			logger.debug("handle data=" + data);
		}

		ArrayList<GeneralEventHandler> list = handlers.get(event);
		if (list == null) {
			if (logger.isDebugEnabled()) {
				logger.debug("list for the event: " + event + ", NOT found");
			}
			return;
		}

		if (logger.isDebugEnabled()) {
			logger.debug("handlers list.size: " + list.size());
		}

		Iterator<GeneralEventHandler> iterator = list.iterator();
		while (iterator.hasNext()) {
			GeneralEventHandler handler = iterator.next();

			if (logger.isDebugEnabled()) {
				logger.debug("handler found: " + handler);
			}

			handler.handle(event, data);
		}
	}

}
