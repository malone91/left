package com.melo.left.config.curator;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ZkCuratorConfig {

    @Value(value = "${zookeeper.url}")
    private String zkUrl;

    @Value(value = "${zookeeper.timeout}")
    private int timeout;

    @Bean
    public CuratorFramework getCuratorFramework(){
        RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 3);
        CuratorFramework client = CuratorFrameworkFactory
                .builder()
                .connectString(zkUrl)
                .connectionTimeoutMs(timeout)
                .sessionTimeoutMs(timeout)
                .retryPolicy(retryPolicy)
                .build();
        client.start();
        return client;
    }
}
