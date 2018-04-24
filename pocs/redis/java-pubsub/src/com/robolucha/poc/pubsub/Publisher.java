package com.robolucha.poc.pubsub;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import redis.clients.jedis.JedisPool;

import java.util.ArrayList;
import java.util.List;

public class Publisher extends Main implements EndOfPublishingListener {

	int finishedThreads;
	int numberOfThreads;

	public Publisher(int numberOfThreads) {
		this.numberOfThreads = numberOfThreads;
		this.finishedThreads = 0;
	}

	public static void main(String[] args) {

		Publisher instance = new Publisher(10);
		instance.runPublishers(Main.REDIS_HOST);
		instance.keepWorking("publisher");

	}

	private void runPublishers(String host) {
		GenericObjectPoolConfig config = new GenericObjectPoolConfig();
		config.setMaxTotal(numberOfThreads);
		JedisPool pool = new JedisPool(config, host);

		List<PublishRunner> runners = new ArrayList<PublishRunner>();

		int counter = 0;
		while (counter < numberOfThreads) {
			runners.add(new PublishRunner(pool.getResource(), Main.QUEUE_NAME, "thread:" + counter, 5000, this));
			counter++;
		}

		runners.parallelStream().forEach(runner -> {
			System.out.println("Starting publisher thread " + runner);
			Thread thread = new Thread(runner);
			thread.start();
		});

	}

	public void onEnd(String id) {
		System.out.println("publish runner finished: " + id);
		finishedThreads++;
		if (finishedThreads >= numberOfThreads) {
			this.alive = false;
		}
	}

}
