package com.melo.gateway;

import com.melo.gateway.session.Configuration;
import com.melo.gateway.session.GenericReferenceSessionFactoryBuilder;
import io.netty.channel.Channel;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

public class ApiTest {

    private final Logger logger = LoggerFactory.getLogger(ApiTest.class);

    /**
     * 注意dubbo版本与dubbo-starter版本要一致，不然启动会报找不到类的错误
     * 启动本地zookeeper 设置audit.enable=true
     * 启动leftdep的provider 将服务注册到zookeeper上
     * 然后启动该ut 访问浏览器 localhost:7397/invoke即可
     * @throws ExecutionException
     * @throws InterruptedException
     */
    @Test
    public void testGenericReference() throws ExecutionException, InterruptedException {
        Configuration configuration = new Configuration();
        configuration.addGenericReference("api-gateway-test", "com.melo.rpc.HelloService", "hello");
        GenericReferenceSessionFactoryBuilder builder = new GenericReferenceSessionFactoryBuilder();
        Future<Channel> future = builder.build(configuration);
        logger.info("future {}", future.get().id());
        TimeUnit.SECONDS.sleep(Long.MAX_VALUE);
    }
}
