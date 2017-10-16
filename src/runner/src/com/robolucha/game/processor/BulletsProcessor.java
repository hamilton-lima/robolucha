package com.robolucha.game.processor;

import org.apache.log4j.Logger;

import com.robolucha.game.action.CheckBulletHitAction;
import com.robolucha.models.Bullet;
import com.robolucha.runner.Calc;
import com.robolucha.runner.MatchRunner;
import com.robolucha.runner.SafeList;

public class BulletsProcessor {

	private static Logger logger = Logger.getLogger(BulletsProcessor.class);

	public SafeList bullets;
	private MatchRunner runner;
	private int halfSize;

	public BulletsProcessor(MatchRunner runner, SafeList bullets2) {
		this.bullets = bullets2;
		this.runner = runner;
		this.halfSize = runner.getGameDefinition().getBulletSize() / 2;
	}

	public void process() {

		logger.debug("----- bullets process(), bullets.size()="
				+ bullets.size());

	  	int pos = 0;
		while (pos < bullets.size()) {
			Bullet bullet = (Bullet) bullets.get(pos++);
			if (bullet == null) {
				continue;
			}
			
			if (logger.isDebugEnabled()) {
				logger.debug(">>> " + bullet);
			}

			moveBullet(bullet);
			checkBulletHit(bullet);
		}

		removeDeadBullets();

	}

	public void moveBullet(Bullet bullet) {
		bullet.move(runner.getDelta());
	}

	public void checkBulletHit(Bullet bullet) {
		CheckBulletHitAction action = new CheckBulletHitAction(runner, bullet);
		runner.runAllActive(action);
	}

	public void removeDeadBullets() {

	  	int pos = 0;
		while (pos < bullets.size()) {
			Bullet bullet = (Bullet) bullets.get(pos);
			if (bullet == null) {
				pos++;
				continue;
			}

			if (!bullet.isActive()) {
				bullets.remove(pos);
				pos++;
				continue;
			}

			if (!Calc.insideTheMapLimits(runner.getGameDefinition(),
					bullet.getX(), bullet.getY(), halfSize)) {
				bullets.remove(pos);
			}
			
			pos++;
		}

	}

	public void cleanup() {
		this.bullets = null;
		this.runner = null;
	}
}