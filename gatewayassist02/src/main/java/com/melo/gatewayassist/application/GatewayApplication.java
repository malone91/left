package com.melo.gatewayassist.application;

import com.alibaba.fastjson.JSON;
import com.melo.gatewayassist.config.GatewayServiceProperties;
import com.melo.gatewayassist.domain.model.aggregates.ApplicationSystemRichInfo;
import com.melo.gatewayassist.domain.service.GatewayCenterService;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

public class GatewayApplication implements ApplicationListener<ContextRefreshedEvent> {

    private GatewayServiceProperties properties;
    private GatewayCenterService gatewayCenterService;

    public GatewayApplication(GatewayServiceProperties properties, GatewayCenterService gatewayCenterService) {
        this.properties = properties;
        this.gatewayCenterService = gatewayCenterService;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        gatewayCenterService.doRegister(properties.getAddress(),
                properties.getGroupId(),
                properties.getGatewayId(),
                properties.getGatewayName(),
                properties.getGatewayAddress());

        ApplicationSystemRichInfo info = gatewayCenterService.pullRichInfo(properties.getAddress(), properties.getGatewayId());
        System.out.println(JSON.toJSON(info));
    }
}
