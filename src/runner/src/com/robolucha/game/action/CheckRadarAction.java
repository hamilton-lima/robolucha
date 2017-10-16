package com.robolucha.game.action;

import java.util.Iterator;
import java.util.LinkedHashMap;

import org.apache.log4j.Logger;

import com.robolucha.game.event.OnFoundEvent;
import com.robolucha.runner.Calc;
import com.robolucha.runner.LuchadorRunner;
import com.robolucha.runner.MatchRunner;

public class CheckRadarAction implements GameAction {

	private static Logger logger = Logger.getLogger(CheckRadarAction.class);
	private MatchRunner matchRunner;
	
	public CheckRadarAction(MatchRunner matchRunner) {
		this.matchRunner = matchRunner;
	}

	public void run(LinkedHashMap<Long, LuchadorRunner> runners,
			LuchadorRunner runner) {

		if (logger.isDebugEnabled()) {
			logger.debug("check radar action : "
					+ runner.getState() );
		}

		Long currentId = runner.getGameComponent().getId();

		double myPosX = runner.getState().getX();
		double myPosY = runner.getState().getY();
		double myRadarAngle = runner.getState().getGunAngle();
		
		double myRadarRangeAngle = matchRunner.getGameDefinition().getRadarAngle();
		double myRadarRadius = matchRunner.getGameDefinition().getRadarRadius();
		
		LuchadorRunner current = null;

		Iterator iterator = runners.keySet().iterator();
		while (iterator.hasNext()) {
			Object key = (Object) iterator.next();
			current = runners.get(key);

			if (logger.isDebugEnabled()) {
				logger.debug("check radar action : active = "
						+ current.isActive());
				logger.debug("check radar action : current = "
						+ current.getState().getPublicState());
			}
			
			if( current == null ){
				continue;
			}

			// se esta ativo
			// se NAO eh o atual
			if (current.isActive()) {

				Long innerId = current.getGameComponent().getId();

				if (logger.isDebugEnabled()) {
					logger.debug("check radar action : mesmo id = "
							+ innerId.equals(currentId));
				}

				if (!innerId.equals(currentId)) {

					try {
						double posX = current.getState().getX();
						double posY = current.getState().getY();

						double dot = Calc.detectEnemy(myPosX, myPosY,
								myRadarAngle, myRadarRangeAngle, myRadarRadius,
								posX, posY);

						if (logger.isDebugEnabled()) {
							logger.debug("current : "
									+ current.getState().getPublicState());
							logger.debug("dot : " + dot);
						}

						if (dot > 0) {
							runner.addEvent(new OnFoundEvent(runner.getState().getPublicState(),
									current.getState().getPublicState(), dot));
						}

					} catch (Exception e) {

						logger.error("error running: " + getName()
								+ " on luchador: "
								+ runner.getGameComponent().getId(), e);
					}

				}

			}

		}

		logger.debug(getName() + ", luchador="
				+ runner.getGameComponent().getName());

	}

	public String getName() {
		return LuchadorRunner.ACTION_CHECK_RADAR;
	}

}
