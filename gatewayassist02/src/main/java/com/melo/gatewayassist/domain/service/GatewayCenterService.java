package com.melo.gatewayassist.domain.service;

import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.melo.gatewayassist.GatewayException;
import com.melo.gatewayassist.common.Result;
import com.melo.gatewayassist.domain.model.aggregates.ApplicationSystemRichInfo;

import java.util.HashMap;
import java.util.Map;

public class GatewayCenterService {

    public void doRegister(String address, String groupId, String gatewayId, String gatewayName, String gatewayAddress) {
        Map<String, Object> params = new HashMap<>();
        params.put("groupId", groupId);
        params.put("gatewayId", gatewayId);
        params.put("gatewayName", gatewayName);
        params.put("gatewayAddress", gatewayAddress);
        String resultStr = HttpUtil.post(address + "/melo/admin/config/registerGateway", params, 3000);
        Result result = JSON.parseObject(resultStr, Result.class);
        if (!"0".equals(result.getCode())) {
            throw new GatewayException("网关服务注册异常");
        }
    }

    public ApplicationSystemRichInfo pullRichInfo(String address, String gatewayId) {
        Map<String, Object> params = new HashMap<>();
        params.put("gatewayId", gatewayId);
        String resultStr = HttpUtil.post(address + "/melo/admin/config/richDetail", params, 3000);
        Result<ApplicationSystemRichInfo> result = JSON.parseObject(resultStr, new TypeReference<Result<ApplicationSystemRichInfo>>() {});
        System.out.println(result);
        if (!"0".equals(result.getCode())) {
            throw new GatewayException("网关服务拉取异常");
        }
        return result.getData();
    }
}
