package com.robolucha.runner;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;

import org.apache.log4j.Logger;

import com.robolucha.models.Luchador;

public class LuchadorCodeChangeListener {

	static Logger logger = Logger.getLogger(LuchadorCodeChangeListener.class);

	private static LuchadorCodeChangeListener instance;
	LinkedHashMap<Long, List<LuchadorRunner>> listeners;

	public LuchadorCodeChangeListener() {
		listeners = new LinkedHashMap<Long, List<LuchadorRunner>>();
		instance = this;
	}

	public void register(LuchadorRunner runner) {
		logger.debug("register " + runner );
		List<LuchadorRunner> list = listeners.get(runner.getGameComponent().getId());
		if (list == null) {
			list = new ArrayList<LuchadorRunner>();
			listeners.put(runner.getGameComponent().getId(), list);
		}
		list.add(runner);
	}

	public void remove(LuchadorRunner runner) {
		logger.debug("remove " + runner);
		List<LuchadorRunner> list = listeners.get(runner.getGameComponent().getId());
		if (list == null) {
			return;
		}
		list.remove(runner);
	}

	public static LuchadorCodeChangeListener getInstance() {
		if (instance == null) {
			instance = new LuchadorCodeChangeListener();
		}
		return instance;
	}

	public void update(Luchador luchador) {
		logger.debug("update  " + luchador);
		if( luchador ==null){
			return;
		}
		
		List<LuchadorRunner> list = listeners.get(luchador.getId());
		if (list != null) {
			Iterator iterator = list.iterator();
			while (iterator.hasNext()) {
				LuchadorRunner runner = (LuchadorRunner) iterator.next();
				logger.debug("found runner to call update, start=" + runner.getStart() );
				runner.update(luchador);
			}
		}
	}

}
