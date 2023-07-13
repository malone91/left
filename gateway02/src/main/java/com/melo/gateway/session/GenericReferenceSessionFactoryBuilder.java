package com.melo.gateway.session;

import com.melo.gateway.session.defaults.GenericReferenceSessionFactory;
import io.netty.channel.Channel;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class GenericReferenceSessionFactoryBuilder {

    public Future<Channel> build(Configuration configuration) {
        GenericReferenceSessionFactory sessionFactory = new GenericReferenceSessionFactory(configuration);
        try {
            return sessionFactory.openSession();
        } catch (ExecutionException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
