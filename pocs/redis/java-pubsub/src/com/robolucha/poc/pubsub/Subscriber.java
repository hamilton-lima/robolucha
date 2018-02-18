package com.robolucha.poc.pubsub;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPubSub;

public class Subscriber extends Main {
	
	public static void main(String[] args) {
		
		Subscriber instance = new Subscriber();
		instance.runSubscriber(Main.REDIS_HOST);
		instance.keepWorking("subscriber");

	}
	
	private void runSubscriber(String host) {

		Jedis jedis = new Jedis(host);

		Thread subscriber = new Thread(new Runnable() {
			public void run() {

				System.out.println("Starting Subscriber");

				jedis.subscribe(new JedisPubSub() {
					@Override
					public void onSubscribe(String channel, int subscribedChannels) {
						super.onSubscribe(channel, subscribedChannels);
						System.out.println("Subscription started: " + channel);
					}

					public void onMessage(String channel, String message) {
						System.out.println(">" + message);
					}

				}, Main.QUEUE_NAME);
			}
		});

		subscriber.start();
	}

}
