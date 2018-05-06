package com.robolucha.game.vo;

import com.robolucha.models.Bullet;
import org.junit.Test;

import static org.junit.Assert.fail;

public class BulletTest {

	@Test
	public void testBulletSingleThread() {
		Bullet[] bullets = getBulletArray();

		for (int i = 0; i < bullets.length; i++) {

			int counter = 0;
			for (int m = 0; m < bullets.length; m++) {
				if (bullets[i].getId() == bullets[m].getId()) {
					counter++;
				}
			}
			if (counter > 1) {
				fail("foi encontrado ID duplicado");
			}
		}

	}

	private Bullet[] getBulletArray() {
		Bullet[] bullets = new Bullet[100];
		for (int i = 0; i < bullets.length; i++) {
			bullets[i] = new Bullet(null, 20, 10, 10, 20);
		}
		return bullets;
	}

	private class Runner implements Runnable {
		Bullet[] bullets = null;

		public void run() {
			bullets = getBulletArray();
		}
	}

	@Test
	public void testBulletMultiThread() throws InterruptedException {

		int runnerCount = 10;

		Bullet[] bullets = new Bullet[runnerCount * 100];
		Runner[] runners = new Runner[runnerCount];
		for (int i = 0; i < runners.length; i++) {
			runners[i] = new Runner();
		}

		for (int i = 0; i < runners.length; i++) {
			new Thread(runners[i]).start();
		}

		int nullCount = 1;

		while (nullCount > 0) {
			nullCount = 0;
			for (int i = 0; i < runners.length; i++) {
				if (runners[i].bullets == null) {
					nullCount++;
				}
			}

			Thread.sleep(10);
		}

		// copia objetos gerados para lista final
		int pos = 0;
		for (int m = 0; m < runners.length; m++) {
			for (int i = 0; i < runners[m].bullets.length; i++) {
				bullets[pos++] = runners[m].bullets[i];
			}
		}

		// pesquisa id duplicados
		for (int i = 0; i < bullets.length; i++) {

			int counter = 0;
			for (int m = 0; m < bullets.length; m++) {
				if (bullets[i].getId() == bullets[m].getId()) {
					counter++;
				}
			}
			if (counter > 1) {
				fail("foi encontrado ID duplicado : " + bullets[i].getId()
						+ " counter = " + counter);
			}
		}

	}

}
