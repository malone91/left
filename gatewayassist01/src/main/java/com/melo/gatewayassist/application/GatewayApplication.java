package com.melo.gatewayassist.application;

import com.melo.gatewayassist.config.GatewayServiceProperties;
import com.melo.gatewayassist.service.RegisterGatewayService;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

public class GatewayApplication implements ApplicationListener<ContextRefreshedEvent> {

    private GatewayServiceProperties properties;
    private RegisterGatewayService registerGatewayService;

    public GatewayApplication(GatewayServiceProperties properties, RegisterGatewayService registerGatewayService) {
        this.properties = properties;
        this.registerGatewayService = registerGatewayService;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        registerGatewayService.doRegister(properties.getAddress(),
                properties.getGroupId(),
                properties.getGatewayId(),
                properties.getGatewayName(),
                properties.getGatewayAddress());
    }
}
