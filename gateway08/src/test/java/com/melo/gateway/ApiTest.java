package com.melo.gateway;

import com.melo.gateway.mapping.HttpCommandType;
import com.melo.gateway.mapping.HttpStatement;
import com.melo.gateway.session.Configuration;
import com.melo.gateway.session.defaults.DefaultGatewaySessionFactory;
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

    /**
     * 通过http网关调用
     * 1、 http://localhost:7397/melo/hello?str=123
     *
     * 2、 curl --location --request POST 'http://localhost:7397/melo/add' \
     * --header 'uId: melo' \
     * --header 'token: eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJtZWxvIiwiZXhwIjoxNjkwMzc0MDc3LCJpYXQiOjE2ODk3NjkyNzcsImtleSI6InBlbGEifQ.6cZB7prTiXOKt3n9CqsaIXYvf3SzUrh7abrzzBHYh6I' \
     * --header 'Content-Type: application/json' \
     * --data-raw '{
     *     "uid": "999",
     *     "name": "tt"
     * }'
     */
    @Test
    public void testApi() throws ExecutionException, InterruptedException {
        Configuration configuration = new Configuration();
        configuration.registerConfig(
                "api-gateway-test",
                "zookeeper://127.0.0.1:2181",
                "com.melo.rpc.HelloService",
                "1.0.0");

        HttpStatement httpStatement01 = new HttpStatement(
                "api-gateway-test",
                "com.melo.rpc.HelloService",
                "hello",
                "java.lang.String",
                "/melo/hello",
                HttpCommandType.GET,
                false
        );
        HttpStatement httpStatement02 = new HttpStatement(
                "api-gateway-test",
                "com.melo.rpc.HelloService",
                "add",
                "com.melo.dto.XReq",
                "/melo/add",
                HttpCommandType.POST,
                true
        );

        configuration.addMapper(httpStatement01);
        configuration.addMapper(httpStatement02);

        DefaultGatewaySessionFactory gatewaySessionFactory = new DefaultGatewaySessionFactory(configuration);
        GatewaySocketServer gatewaySocketServer = new GatewaySocketServer(configuration, gatewaySessionFactory);
        Future<Channel> submit = Executors.newFixedThreadPool(2).submit(gatewaySocketServer);
        Channel channel = submit.get();
        if (channel == null) {
            throw new RuntimeException("netty start fail");
        }

        while (!channel.isActive()) {
            logger.info("netty server is starting");
            TimeUnit.MILLISECONDS.sleep(500);
        }

        logger.info("netty server start");

        TimeUnit.SECONDS.sleep(Long.MAX_VALUE);
    }
}
