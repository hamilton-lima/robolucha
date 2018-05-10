package com.robolucha.publisher;

import org.apache.log4j.Logger;

import java.io.IOException;

public class RedisDockerHelper {
    private static final String DOCKER_REDIS_START = "docker run --name test-redis --rm -p 6379:6379 -d redis";
    private static final String DOCKER_REDIS_STOP = "docker stop test-redis";
    private Logger logger = Logger.getLogger(RedisDockerHelper.class);

    private Thread processThread;

    public void start() {

        processThread = new Thread(new Runnable() {
            public void run() {
                try {
                    Runtime rt = Runtime.getRuntime();
                    Process proc = rt.exec(DOCKER_REDIS_START);
                } catch (Exception e) {
                    logger.error(e);
                }
            }
        });

        processThread.start();
    }

    public void stop() throws IOException {
        Runtime rt = Runtime.getRuntime();
        Process proc = rt.exec(DOCKER_REDIS_STOP);
        processThread.interrupt();
    }

}
