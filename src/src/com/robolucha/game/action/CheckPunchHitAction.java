package com.robolucha.game.action;

import java.util.LinkedHashMap;

import org.apache.log4j.Logger;

import com.robolucha.game.event.OnGotDamageEvent;
import com.robolucha.old.Punch;
import com.robolucha.runner.LuchadorMatchState;
import com.robolucha.runner.LuchadorRunner;
import com.robolucha.runner.MatchRunner;
import com.robolucha.shared.Calc;

public class CheckPunchHitAction implements GameAction {

	private static Logger logger = Logger.getLogger(CheckPunchHitAction.class);

	private Punch punch;

	private MatchRunner matchRunner;

	public CheckPunchHitAction(MatchRunner matchRunner, Punch punch) {
		this.matchRunner = matchRunner;
		this.punch = punch;
	}

	public void run(LinkedHashMap<Long, LuchadorRunner> runners, LuchadorRunner runner) {

		if (punch.isActive()) {

			if (!punch.getOwner().getGameComponent().getId().equals(runner.getGameComponent().getId())) {

				LuchadorMatchState current = punch.getOwner().getState();

				double myPosX = current.getX();
				double myPosY = current.getY();
				double myRadarAngle = current.getAngle();
				double myRadarRangeAngle = matchRunner.getGameDefinition().getPunchAngle();

				// o alcance do soco eh o mesmo tamanho do lutchador
				int myRadarRadius = (int) (punch.getOwner().getSize() * 1.5);

				double posX = runner.getState().getX();
				double posY = runner.getState().getY();

				double dot = Calc.detectEnemy(myPosX, myPosY, myRadarAngle, myRadarRangeAngle, myRadarRadius, posX,
						posY);

				logger.debug("** DOT = " + dot);

				// the bullet hit the target
				if (dot >= 0) {

					punch.setActive(false);
					runner.damage(punch.getAmount());

					// notifica o gerenciador de eventos que o robo faleceu ...
					// tadimmm :D e morreu de soco !!!
					if (runner.getState().getLife() <= 0) {
						matchRunner.getEventHandler().kill(punch.getOwner(), runner);
					}

					runner.addEvent(new OnGotDamageEvent(punch.getOwner().getState().getPublicState(), runner
							.getState().getPublicState(), punch.getAmount()));

					matchRunner.getEventHandler().damage(punch.getOwner(), runner, punch.getAmount());
				}
			}
		}

	}

	public String getName() {
		return "checkPunchHit";
	}

}
