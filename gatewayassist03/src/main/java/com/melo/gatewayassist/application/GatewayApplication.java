package com.melo.gatewayassist.application;

import cn.hutool.core.collection.CollectionUtil;
import com.alibaba.fastjson.JSON;
import com.melo.gateway.core.mapping.HttpCommandType;
import com.melo.gateway.core.mapping.HttpStatement;
import com.melo.gateway.core.session.Configuration;
import com.melo.gatewayassist.config.GatewayServiceProperties;
import com.melo.gatewayassist.domain.model.aggregates.ApplicationSystemRichInfo;
import com.melo.gatewayassist.domain.model.vo.ApplicationInterfaceMethodVO;
import com.melo.gatewayassist.domain.model.vo.ApplicationInterfaceVO;
import com.melo.gatewayassist.domain.model.vo.ApplicationSystemVO;
import com.melo.gatewayassist.service.GatewayCenterService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

import java.util.List;

public class GatewayApplication implements ApplicationListener<ContextRefreshedEvent> {

    private final Logger logger = LoggerFactory.getLogger(GatewayApplication.class);

    private GatewayServiceProperties properties;
    private GatewayCenterService gatewayCenterService;
    private Configuration configuration;

    public GatewayApplication(GatewayServiceProperties properties, GatewayCenterService gatewayCenterService, Configuration configuration) {
        this.properties = properties;
        this.gatewayCenterService = gatewayCenterService;
        this.configuration = configuration;
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
        List<ApplicationSystemVO> list = info.getList();
        if (CollectionUtil.isEmpty(list)) {
            return;
        }
        for (ApplicationSystemVO systemVO : list) {
            List<ApplicationInterfaceVO> interfaceList = systemVO.getInterfaceList();
            if (CollectionUtil.isEmpty(list)) {
                continue;
            }
            for (ApplicationInterfaceVO interfaceVO : interfaceList) {
                configuration.registerConfig(systemVO.getSystemId(),
                        systemVO.getSystemRegistry(),
                        interfaceVO.getInterfaceId(),
                        interfaceVO.getInterfaceVersion());
                List<ApplicationInterfaceMethodVO> methodList = interfaceVO.getMethodList();
                if (CollectionUtil.isEmpty(methodList)) {
                    continue;
                }
                for (ApplicationInterfaceMethodVO methodVO : methodList) {
                    HttpStatement httpStatement = new HttpStatement(
                            systemVO.getSystemId(),
                            interfaceVO.getInterfaceId(),
                            methodVO.getMethodId(),
                            methodVO.getParameterType(),
                            methodVO.getUri(),
                            HttpCommandType.valueOf(methodVO.getHttpCommandType()),
                            methodVO.isAuth());
                    configuration.addMapper(httpStatement);
                    logger.info("systemId {} interfaceId {} methodId {}", systemVO.getSystemId(), interfaceVO.getInterfaceId(), methodVO.getMethodId());
                }
            }
        }
    }
}
