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
import io.netty.channel.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextClosedEvent;

import java.util.List;

/**
 * 把网关的注册和拉取配置操作放到setApplicationContext中，如果失败了直接抛出异常关闭容器
 */
public class GatewayApplication implements ApplicationContextAware, ApplicationListener<ContextClosedEvent> {

    private final Logger logger = LoggerFactory.getLogger(GatewayApplication.class);

    private GatewayServiceProperties properties;
    private GatewayCenterService gatewayCenterService;
    private Configuration configuration;
    private Channel channel;

    public GatewayApplication(GatewayServiceProperties properties, GatewayCenterService gatewayCenterService, Configuration configuration, Channel channel) {
        this.properties = properties;
        this.gatewayCenterService = gatewayCenterService;
        this.configuration = configuration;
        this.channel = channel;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        try {
            gatewayCenterService.doRegister(properties.getAddress(),
                    properties.getGroupId(),
                    properties.getGatewayId(),
                    properties.getGatewayName(),
                    properties.getGatewayAddress());
            addMappers("");
        } catch (Exception e) {
            logger.error("网关启动失败，停止服务 {}", e.getMessage(), e);
            throw e;
        }
    }

    public void addMappers(String systemId) {
        ApplicationSystemRichInfo info = gatewayCenterService.pullRichInfo(properties.getAddress(), properties.getGatewayId(), systemId);
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

    @Override
    public void onApplicationEvent(ContextClosedEvent contextClosedEvent) {
        try {
            if (channel.isActive()) {
                logger.info("应用容器关闭，api网关服务关闭 address {}", channel.localAddress());
                channel.close();
            }
        } catch (Exception e) {
            logger.error("应用容器关闭时关闭api网关服务失败", e);
        }
    }

    public void receiveMessage(Object message) {
        logger.info("注册中心推送消息 {}", message);
        addMappers(message.toString().substring(1, message.toString().length() - 1));
    }
}
