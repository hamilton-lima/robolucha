package com.robolucha.publisher;

import com.google.gson.Gson;
import com.robolucha.runner.Config;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.subjects.BehaviorSubject;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPubSub;

public class RemoteQueue {

    Jedis redis;
    private Gson gson;

    public RemoteQueue(Config config) {
        GenericObjectPoolConfig redisConfig = new GenericObjectPoolConfig();
        JedisPool redisPool = new JedisPool(redisConfig, config.getRedisHost());
        this.redis = redisPool.getResource();

        this.gson = new Gson();
    }

    public Observable<Long> publish(Object subjectToPublish) {
        String channel = getChannelName(subjectToPublish);
        String data = getData(subjectToPublish);
        this.redis.publish(channel, data);

        return Observable.just(this.redis.publish(channel, data));
    }

    private String getData(Object subjectToPublish) {
        return this.gson.toJson(subjectToPublish);
    }

    private String getChannelName(Class clazz) {
        return clazz.getCanonicalName();
    }

    private String getChannelName(Object subjectToPublish) {
        return getChannelName(subjectToPublish.getClass());
    }

    public <T> BehaviorSubject subscribe(Class<T> clazzToSubscribe) {

        BehaviorSubject<T> result = BehaviorSubject.create();
        String channel = getChannelName(clazzToSubscribe);

        Thread subscriber = new Thread(new Runnable() {
            public void run() {

                redis.subscribe(new JedisPubSub() {
                    @Override
                    public void onSubscribe(String channel, int subscribedChannels) {
                        super.onSubscribe(channel, subscribedChannels);
                    }

                    public void onMessage(String channel, String message) {
                        T data = gson.fromJson( message, clazzToSubscribe);
                        result.onNext( data );
                    }

                }, channel);
            }
        });

        result.subscribe(new Observer<T>() {
            @Override
            public void onSubscribe(Disposable disposable) {

            }

            @Override
            public void onNext(T t) {

            }

            @Override
            public void onError(Throwable throwable) {

            }

            @Override
            public void onComplete() {
                subscriber.interrupt();
            }
        });

        subscriber.start();
        return result;
    }
}
