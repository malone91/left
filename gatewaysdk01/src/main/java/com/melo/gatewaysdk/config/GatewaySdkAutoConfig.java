package com.melo.gatewaysdk.config;

import com.melo.gatewaysdk.application.GatewaySdkApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(GatewaySdkServerProperties.class)
public class GatewaySdkAutoConfig {

    @Bean
    public GatewaySdkApplication gatewaySdkApplication(GatewaySdkServerProperties properties) {
        return new GatewaySdkApplication(properties);
    }
}
