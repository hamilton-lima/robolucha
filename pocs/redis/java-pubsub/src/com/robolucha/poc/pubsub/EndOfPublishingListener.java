package com.robolucha.poc.pubsub;

public interface EndOfPublishingListener {

	void onEnd(String id);
}
