package com.melo.gatewayassist.config;

import com.melo.gateway.core.session.defaults.DefaultGatewaySessionFactory;
import com.melo.gateway.core.socket.GatewaySocketServer;
import com.melo.gatewayassist.application.GatewayApplication;
import com.melo.gatewayassist.service.GatewayCenterService;
import io.netty.channel.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

@Configuration
@EnableConfigurationProperties(GatewayServiceProperties.class)
public class GatewayAutoConfig {

    private final Logger logger = LoggerFactory.getLogger(GatewayAutoConfig.class);

    @Bean
    public GatewayCenterService gatewayCenterService() {
        return new GatewayCenterService();
    }

    @Bean
    public GatewayApplication gatewayApplication(GatewayServiceProperties properties, GatewayCenterService gatewayCenterService,
                                                 com.melo.gateway.core.session.Configuration configuration,
                                                 Channel channel) {
        return new GatewayApplication(properties, gatewayCenterService, configuration, channel);
    }

    @Bean
    public com.melo.gateway.core.session.Configuration gatewayCoreConfiguration(GatewayServiceProperties properties) {
        com.melo.gateway.core.session.Configuration configuration = new com.melo.gateway.core.session.Configuration();
        String[] split = properties.getGatewayAddress().split(":");
        configuration.setHostName(split[0].trim());
        configuration.setPort(Integer.valueOf(split[1].trim()));
        return configuration;
    }

    @Bean
    public Channel initGateway(com.melo.gateway.core.session.Configuration configuration) throws ExecutionException, InterruptedException {
        DefaultGatewaySessionFactory sessionFactory = new DefaultGatewaySessionFactory(configuration);
        GatewaySocketServer socketServer = new GatewaySocketServer(configuration, sessionFactory);
        Future<Channel> future = Executors.newFixedThreadPool(2).submit(socketServer);
        Channel channel = future.get();
        if (channel == null) {
            throw new RuntimeException("api gateway core netty start error channel is null");
        }
        while (!channel.isActive()) {
            logger.info("api gateway core netty server is starting");
            TimeUnit.SECONDS.sleep(1L);
        }
        logger.info("api gateway core netty server gateway started address {}", channel.localAddress());
        return channel;
    }
}
