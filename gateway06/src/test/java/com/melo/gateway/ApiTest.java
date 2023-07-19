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
     * --header 'Content-Type: application/json' \
     * --data-raw '{
     *     "uid": "999",
     *     "name": "tt"
     * }'
     * 参数名称错误不会报错，值为空
     */
    @Test
    public void testApi() throws ExecutionException, InterruptedException {
        Configuration configuration = new Configuration();

        HttpStatement httpStatement01 = new HttpStatement(
                "api-gateway-test",
                "com.melo.rpc.HelloService",
                "hello",
                "java.lang.String",
                "/melo/hello",
                HttpCommandType.GET
        );
        HttpStatement httpStatement02 = new HttpStatement(
                "api-gateway-test",
                "com.melo.rpc.HelloService",
                "add",
                "com.melo.dto.XReq",
                "/melo/add",
                HttpCommandType.POST
        );

        configuration.addMapper(httpStatement01);
        configuration.addMapper(httpStatement02);

        DefaultGatewaySessionFactory gatewaySessionFactory = new DefaultGatewaySessionFactory(configuration);
        GatewaySocketServer gatewaySocketServer = new GatewaySocketServer(gatewaySessionFactory);
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
