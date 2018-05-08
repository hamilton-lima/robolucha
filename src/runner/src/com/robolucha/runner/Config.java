package com.robolucha.runner;

public class Config {

    public static final String REDIS_HOST = "REDIS_HOST";
    public static final String DEFAULT_REDIS_HOST = "localhost";

    private String redisHost;

    private static Config instance;

    private Config() {
        String redisHost = System.getenv(REDIS_HOST);
        if (isValidHost(redisHost)) {
            this.redisHost = redisHost;
        } else {
            this.redisHost = DEFAULT_REDIS_HOST;
        }
    }

    boolean isValidHost(String redisHost) {
        return redisHost != null && !redisHost.trim().isEmpty();
    }

    static void reset(){
        instance = null;
    }

    public static Config getInstance() {
        if (instance == null) {
            instance = new Config();
        }
        return instance;
    }

    public String getRedisHost() {
        return redisHost;
    }
}
