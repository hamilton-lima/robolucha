package com.robolucha.poc.pubsub;

public abstract class Main {

	protected static final String QUEUE_NAME = "queue1";
	protected static final String REDIS_HOST = "localhost";
	protected boolean alive = true; 

	protected synchronized void keepWorking(String title) {

		while (alive) {
			System.out.println("I'm alive, " + title);
			try {
				this.wait(2000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

	}

}
