package com.robolucha.publisher;

import org.apache.log4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class RedisDockerHelper {
    private static final String DOCKER_REDIS_START = "docker run --name test-redis --rm -p 6379:6379 -d redis";
    private static final String DOCKER_REDIS_STOP = "docker stop test-redis";
    private Logger logger = Logger.getLogger(RedisDockerHelper.class);

    private Thread processThread;

    public void start() throws IOException {
        run(DOCKER_REDIS_START);
    }

    public void stop() throws IOException {
        run(DOCKER_REDIS_STOP);
    }

    private void run(String command) throws IOException {
        logger.debug("RUN:" + command);
        Runtime rt = Runtime.getRuntime();
        Process proc = rt.exec(command);

        BufferedReader stdInput = new BufferedReader(new InputStreamReader(proc.getInputStream()));
        BufferedReader stdError = new BufferedReader(new InputStreamReader(proc.getErrorStream()));

        logAllLines("STDOUT", stdInput);
        logAllLines("STDERR", stdError);
    }

    private void logAllLines(String title, BufferedReader reader) throws IOException {
        logger.debug(title);
        String line = reader.readLine();
        while (line != null) {
            logger.debug(line);
            line = reader.readLine();
        }
    }

}
