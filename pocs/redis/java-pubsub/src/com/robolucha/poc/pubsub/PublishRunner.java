package com.robolucha.poc.pubsub;

import redis.clients.jedis.Jedis;

public class PublishRunner implements Runnable {
	private Jedis jedis;
	private String queue;
	private String id;
	private int threshold;
	private EndOfPublishingListener listener;

	public PublishRunner(Jedis jedis, String queue, String id, int threshold, EndOfPublishingListener listener) {
		this.jedis = jedis;
		this.queue = queue;
		this.id = id;
		this.threshold = threshold;
		this.listener = listener;
	}

	@Override
	public void run() {
		int counter = 0;
		String message;
		while( counter < this.threshold) {
			message = this.id + ":" + Double.toHexString(Math.random() * 100).toUpperCase(); 
			jedis.publish(queue, message );
			counter ++;
		}
		listener.onEnd(id);
	}

	@Override
	public String toString() {
		return "PublishRunner [jedis=" + jedis + ", queue=" + queue + ", id=" + id + ", threshold=" + threshold + "]";
	}

	
}
