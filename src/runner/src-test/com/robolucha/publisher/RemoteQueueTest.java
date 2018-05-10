package com.robolucha.publisher;

import com.robolucha.models.Code;
import com.robolucha.models.Luchador;
import com.robolucha.runner.Config;
import com.robolucha.test.MockLuchador;
import io.reactivex.functions.Consumer;
import io.reactivex.subjects.BehaviorSubject;
import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

import static junit.framework.TestCase.assertEquals;

public class RemoteQueueTest {
    RedisDockerHelper docker = new RedisDockerHelper();
    private Logger logger = Logger.getLogger(RemoteQueueTest.class);

    @Before
    public void setUp() throws Exception {
        docker.start();
    }

    @After
    public void tearDown() throws Exception {
        docker.stop();
    }

    @Test
    public void publishAndSubscribe() throws Exception {

        try (RemoteQueue queue = new RemoteQueue(Config.getInstance())) {

            BehaviorSubject<Luchador> subject = queue.subscribe(Luchador.class);
            CompletableFuture<String> future = new CompletableFuture<>();

            subject.subscribe(new Consumer<Luchador>() {
                @Override
                public void accept(Luchador luchador) throws Exception {
                    logger.info("Luchador from REDIS subscription=" + luchador.toString());

                    assertEquals(luchador.getId(), 2L);
                    assertEquals(luchador.getCodes().size(), 1);

                    Code code = luchador.getCodes().stream().findFirst().get();
                    assertEquals("start", code.getEvent());
                    assertEquals("move(10)", code.getScript());
                    future.complete("subscribe");
                }
            });

            Luchador foo = MockLuchador.build(2L, "start", "move(10)");
            queue.publish(foo);

            assertEquals("subscribe", future.get(5, TimeUnit.SECONDS));
        }
    }

}