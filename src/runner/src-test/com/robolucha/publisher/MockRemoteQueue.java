package com.robolucha.publisher;

import io.reactivex.Observable;

public class MockRemoteQueue extends RemoteQueue {
    public Object lastPublished;

    MockRemoteQueue() {
    }

    @Override
    public Observable<Long> publish(String channel, Object subjectToPublish) {
        lastPublished = subjectToPublish;
        return Observable.just(0L);
    }
}
