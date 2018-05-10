package com.robolucha.runner;

public class Config {

    public static final String REDIS_HOST = "REDIS_HOST";
    public static final String REDIS_PORT = "REDIS_PORT";

    public static final String DEFAULT_REDIS_HOST = "localhost";
    public static final int DEFAULT_REDIS_PORT = 6379;

    private String redisHost;
    private int redisPort;

    private static Config instance;

    private Config() {
        setRedisHost();
        setRedisPort();
    }

    private void setRedisHost() {
        String redisHost = System.getenv(REDIS_HOST);
        if (isValidString(redisHost)) {
            this.redisHost = redisHost;
        } else {
            this.redisHost = DEFAULT_REDIS_HOST;
        }
    }

    private void setRedisPort() {
        try {
            this.redisPort = Integer.parseInt(System.getenv(REDIS_PORT));
        } catch (NumberFormatException e) {
            this.redisPort = DEFAULT_REDIS_PORT;
        }
    }

    boolean isValidString(String redisHost) {
        return redisHost != null && !redisHost.trim().isEmpty();
    }

    static void reset() {
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

    public int getRedisPort() {
        return redisPort;
    }

}
