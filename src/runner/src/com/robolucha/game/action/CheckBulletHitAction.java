package com.robolucha.game.action;

import java.util.LinkedHashMap;

import org.apache.log4j.Logger;

import com.robolucha.game.event.OnGotDamageEvent;
import com.robolucha.models.Bullet;
import com.robolucha.runner.Calc;
import com.robolucha.runner.LuchadorRunner;
import com.robolucha.runner.MatchRunner;

public class CheckBulletHitAction implements GameAction {

	private static Logger logger = Logger.getLogger(CheckBulletHitAction.class);

	private Bullet bullet;

	private MatchRunner matchRunner;

	public CheckBulletHitAction(MatchRunner matchRunner, Bullet bullet) {
		this.bullet = bullet;
		this.matchRunner = matchRunner;
	}

	public void run(LinkedHashMap<Long, LuchadorRunner> runners, LuchadorRunner runner) {

		if (logger.isDebugEnabled()) {
			logger.debug("checkbullethit, bullet=" + bullet);
			logger.debug("checkbullethit, runner=" + runner);
		}

		if (bullet.isActive()) {

			if (!bullet.getOwner().getGameComponent().getId().equals(runner.getGameComponent().getId())) {

				if (logger.isDebugEnabled()) {
					logger.debug("checkbullethit, conferindo com outro luchador=" + runner);
				}

				// the bullet hit the target
				if (Calc.intersectBullet(runner, bullet)) {

					bullet.setActive(false);
					runner.damage(bullet.getAmount());

					if (logger.isDebugEnabled()) {
						logger.debug("checkbullethit, dano aplicado = " + bullet.getAmount());
					}

					// notifica o gerenciador de eventos que o robo faleceu ...
					// tadimmm :D
					if (runner.getState().getLife() <= 0) {

						if (logger.isDebugEnabled()) {
							logger.debug("checkbullethit, luchador MORREU !! = " + runner);
						}

						matchRunner.getEventHandler().kill(bullet.getOwner(), runner);
					}

					runner.addEvent(new OnGotDamageEvent(bullet.getOwner().getState().getPublicState(), runner
							.getState().getPublicState(), bullet.getAmount()));

					matchRunner.getEventHandler().damage(bullet.getOwner(), runner, bullet.getAmount());
				}
			}
		}

	}

	public String getName() {
		return "checkBulletHit";
	}

}
