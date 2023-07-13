package com.melo.gateway.session.defaults;

import com.melo.gateway.session.Configuration;
import com.melo.gateway.session.IGenericReferenceSessionFactory;
import com.melo.gateway.session.SessionServer;
import io.netty.channel.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

public class GenericReferenceSessionFactory implements IGenericReferenceSessionFactory {

    private final Logger logger = LoggerFactory.getLogger(GenericReferenceSessionFactory.class);
    private final Configuration configuration;

    public GenericReferenceSessionFactory(Configuration configuration) {
        this.configuration = configuration;
    }

    @Override
    public Future<Channel> openSession() throws ExecutionException, InterruptedException {
        SessionServer sessionServer = new SessionServer(configuration);
        Future<Channel> future = Executors.newFixedThreadPool(2).submit(sessionServer);
        Channel channel = future.get();
        if (channel == null) {
            throw new RuntimeException("netty server start error");
        }

        while (!channel.isActive()) {
            logger.info("netty server gateway running");
            TimeUnit.MILLISECONDS.sleep(500);
        }

        logger.info("netty server start done {}", channel.localAddress());
        return future;
    }
}
