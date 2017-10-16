package com.robolucha.game.processor;

import org.apache.log4j.Logger;

import com.robolucha.game.action.CheckPunchHitAction;
import com.robolucha.old.Punch;
import com.robolucha.runner.MatchRunner;
import com.robolucha.runner.SafeList;

public class PunchesProcessor {

	private static Logger logger = Logger.getLogger(PunchesProcessor.class);

	public SafeList punches;
	private MatchRunner runner;

	public PunchesProcessor(MatchRunner runner, SafeList punches2) {
		this.runner = runner;
		this.punches = punches2;
	}
	
	public void cleanup(){
		this.runner = null;
		this.punches = null;
	}

	/**
	 * testa colisao do soco somente uma vez apos isto remove da lista.
	 */
	public void process() {
		logger.debug("----- punches process(), punches.size()="
				+ punches.size());

		synchronized (punches) {

			int pos = 0;
			while (pos < punches.size()) {
				Punch punch = (Punch) punches.get(pos++);
				if (punch == null) {
					continue;
				}

				if (logger.isDebugEnabled()) {
					logger.debug(">>> " + punch);
				}

				if (punch.isActive()) {
					checkPunchHit(punch);
					punch.setActive(false);
				}
			}
		}
		removeInactivePunches();
	}

	private void checkPunchHit(Punch punch) {
		CheckPunchHitAction action = new CheckPunchHitAction(runner, punch);
		runner.runAllActive(action);
	}

	public void removeInactivePunches() {

		int pos = 0;
		while (pos < punches.size()) {
			Punch punch = (Punch) punches.get(pos);
			if (punch == null) {
				pos++;
				continue;
			}

			if (!punch.isActive()) {
				punches.remove(pos);
			}
			
			pos++;
		}
	}

}