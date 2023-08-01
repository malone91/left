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
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisClientConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
import redis.clients.jedis.JedisPoolConfig;

import java.time.Duration;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

@Configuration
@EnableConfigurationProperties(GatewayServiceProperties.class)
public class GatewayAutoConfig {

    private final Logger logger = LoggerFactory.getLogger(GatewayAutoConfig.class);

    @Bean
    public RedisConnectionFactory redisConnectionFactory(GatewayServiceProperties properties, GatewayCenterService gatewayCenterService) {
        Map<String, String> redisConfig = gatewayCenterService.queryRedisConfig(properties.getAddress());
        RedisStandaloneConfiguration configuration = new RedisStandaloneConfiguration();
        configuration.setHostName(redisConfig.get("host"));
        configuration.setPort(Integer.parseInt(redisConfig.get("port")));
        JedisPoolConfig poolConfig = new JedisPoolConfig();
        poolConfig.setMaxTotal(100);
        poolConfig.setMaxWaitMillis(30 * 1000);
        poolConfig.setMinIdle(20);
        poolConfig.setMaxIdle(40);
        poolConfig.setTestWhileIdle(true);

        JedisClientConfiguration clientConfiguration = JedisClientConfiguration.builder()
                .connectTimeout(Duration.ofSeconds(2))
                .clientName("api-gateway-assist-redis-" + properties.getGatewayId())
                .usePooling()
                .poolConfig(poolConfig)
                .build();
        return new JedisConnectionFactory(configuration, clientConfiguration);
    }

    @Bean
    public RedisMessageListenerContainer container(GatewayServiceProperties properties, RedisConnectionFactory factory, MessageListenerAdapter adapter) {
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(factory);
        container.addMessageListener(adapter, new PatternTopic(properties.getGatewayId()));
        return container;
    }

    @Bean
    public MessageListenerAdapter adapter(GatewayApplication application) {
        return new MessageListenerAdapter(application, "receiveMessage");
    }

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
