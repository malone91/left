package com.melo.gateway.session;

import io.netty.channel.Channel;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

public class ApiTest {

    private final Logger logger = LoggerFactory.getLogger(ApiTest.class);

    @Test
    public void test() throws ExecutionException, InterruptedException {
        SessionServer sessionServer = new SessionServer();
        Future<Channel> future = Executors.newFixedThreadPool(2).submit(sessionServer);
        Channel channel = future.get();
        if (channel == null) {
            throw new RuntimeException("netty server start error channel is null");
        }
        while (!channel.isActive()) {
            logger.info("netty server start success");
            TimeUnit.MILLISECONDS.sleep(500);
        }
        logger.info("netty server starat done {}", channel.localAddress());
        TimeUnit.SECONDS.sleep(Long.MAX_VALUE);
    }
}
