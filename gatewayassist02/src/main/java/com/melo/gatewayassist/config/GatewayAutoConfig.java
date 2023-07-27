package com.melo.gatewayassist.config;

import com.melo.gatewayassist.application.GatewayApplication;
import com.melo.gatewayassist.domain.service.GatewayCenterService;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(GatewayServiceProperties.class)
public class GatewayAutoConfig {

    @Bean
    public GatewayCenterService registerGatewayService() {
        return new GatewayCenterService();
    }

    @Bean
    public GatewayApplication gatewayApplication(GatewayServiceProperties properties, GatewayCenterService gatewayCenterService) {
        return new GatewayApplication(properties, gatewayCenterService);
    }
}
