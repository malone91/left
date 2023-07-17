package com.melo.gateway;

import com.melo.gateway.mapping.HttpCommandType;
import com.melo.gateway.mapping.HttpStatement;
import com.melo.gateway.session.Configuration;
import com.melo.gateway.session.defalt.DefaultGatewaySessionFactory;
import com.melo.gateway.socket.GatewaySocketServer;
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

    /***
     * localhost:7397/melo/hello
     * @throws ExecutionException
     * @throws InterruptedException
     */
    @Test
    public void testRpc() throws ExecutionException, InterruptedException {
        Configuration configuration = new Configuration();
        HttpStatement httpStatement = new HttpStatement("api-gateway-test",
                "com.melo.rpc.HelloService", "hello", "/melo/hello", HttpCommandType.GET);
        configuration.addMapper(httpStatement);
        DefaultGatewaySessionFactory sessionFactory = new DefaultGatewaySessionFactory(configuration);
        GatewaySocketServer gatewaySocketServer = new GatewaySocketServer(sessionFactory);
        Future<Channel> future = Executors.newFixedThreadPool(2).submit(gatewaySocketServer);
        Channel channel = future.get();
        if (channel == null) {
            logger.error("netty server start fail");
        }
        while (!channel.isActive()) {
            logger.info("netty server starting");
            TimeUnit.MILLISECONDS.sleep(500);
        }
        logger.info("netty server start");

        TimeUnit.SECONDS.sleep(Long.MAX_VALUE);
    }
}
