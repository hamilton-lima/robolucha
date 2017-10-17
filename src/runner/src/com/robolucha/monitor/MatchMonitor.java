package com.robolucha.monitor;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import com.robolucha.runner.MatchRunner;

public class MatchMonitor {
	private Map<Long, MatchRunner> matches;

	private static MatchMonitor instance;

	private MatchMonitor() {
		this.matches = Collections.synchronizedMap(new HashMap<Long, MatchRunner>());
	}

	public static MatchMonitor getInstance() {
		if (instance == null) {
			instance = new MatchMonitor();
		}
		return instance;
	}

	public void add(Long matchRunId, MatchRunner runner) {
		matches.put(matchRunId, runner);
	}

	public MatchRunner get(Long Id) {
		return (MatchRunner) matches.get(Id);
	}

	public void remove(Long Id) {
		matches.remove(Id);
	}
	
	public void killThenAll(){
		MatchRunner runners[] = new MatchRunner[0];
		runners = matches.values().toArray(runners);
		for (int i = 0; i < runners.length; i++) {
			runners[i].kill();
		}
	}
}
