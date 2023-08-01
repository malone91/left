package com.melo.gatewayassist.service;

import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.melo.gatewayassist.GatewayException;
import com.melo.gatewayassist.common.Result;
import com.melo.gatewayassist.domain.model.aggregates.ApplicationSystemRichInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

public class GatewayCenterService {

    private Logger logger = LoggerFactory.getLogger(GatewayCenterService.class);

    public void doRegister(String address, String groupId, String gatewayId, String gatewayName, String gatewayAddress) {
        Map<String, Object> params = new HashMap<>();
        params.put("groupId", groupId);
        params.put("gatewayId", gatewayId);
        params.put("gatewayName", gatewayName);
        params.put("gatewayAddress", gatewayAddress);
        String resultStr = null;
        try {
            resultStr = HttpUtil.post(address + "/melo/admin/config/registerGateway", params, 3000);
        } catch (Exception e) {
            logger.error("网关注册服务异常 address {}", "/melo/admin/config/registerGateway");
            throw e;
        }
        Result result = JSON.parseObject(resultStr, Result.class);
        if (!"0".equals(result.getCode())) {
            throw new GatewayException("网关服务注册异常");
        }
    }

    public ApplicationSystemRichInfo pullRichInfo(String address, String gatewayId, String systemId) {
        Map<String, Object> params = new HashMap<>();
        params.put("gatewayId", gatewayId);
        params.put("systemId", systemId);
        String resultStr = null;
        try {
            resultStr = HttpUtil.post(address + "/melo/admin/config/richDetail", params, 3000);
        } catch (Exception e) {
            logger.error("网关拉取服务异常 address {}", "/melo/admin/config/richDetail");
            throw e;
        }
        Result<ApplicationSystemRichInfo> result = JSON.parseObject(resultStr, new TypeReference<Result<ApplicationSystemRichInfo>>() {
        });
        System.out.println(result);
        if (!"0".equals(result.getCode())) {
            throw new GatewayException("网关服务拉取异常");
        }
        return result.getData();
    }

    public Map<String, String> queryRedisConfig(String address) {
        String resultStr;
        try {
            resultStr = HttpUtil.post(address + "/melo/admin/config/queryRedisConfig", "", 1550);
        } catch (Exception e) {
            logger.error("网关服务拉取配置异常，链接资源不可用：{}", address + "/melo/admin/config/queryRedisConfig", e);
            throw e;
        }
        Result<Map<String, String>> result = JSON.parseObject(resultStr, new TypeReference<Result<Map<String, String>>>() {
        });
        logger.info("从网关中心拉取Redis配置信息完成。result：{}", resultStr);
        if (!"0000".equals(result.getCode()))
            throw new GatewayException("从网关中心拉取Redis配置信息异常");
        return result.getData();
    }

}
