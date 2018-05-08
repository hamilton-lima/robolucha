package com.robolucha.publisher;

import io.reactivex.Observable;

public class RemoteQueue {

    public Observable<Boolean> publish(Object subjectToPublish){
        return Observable.just(Boolean.TRUE);
    }

    //TODO: add super class for the Models
    public <T>Observable subscribe(Class <T> clazzToSubscribe){

        // TODO: define the queue name based on the class full name
        return Observable.just("empty");
    }
}
