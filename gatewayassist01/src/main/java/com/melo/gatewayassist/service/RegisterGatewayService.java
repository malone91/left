package com.melo.gatewayassist.service;

import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSON;
import com.melo.gatewayassist.GatewayException;
import com.melo.gatewayassist.common.Result;

import java.util.HashMap;
import java.util.Map;

public class RegisterGatewayService {

    public void doRegister(String address, String groupId, String gatewayId, String gatewayName, String gatewayAddress) {
        Map<String, Object> params = new HashMap<>();
        params.put("groupId", groupId);
        params.put("gatewayId", gatewayId);
        params.put("gatewayName", gatewayName);
        params.put("gatewayAddress", gatewayAddress);
        String resultStr = HttpUtil.post(address, params, 350);
        Result result = JSON.parseObject(resultStr, Result.class);
        if (!"0".equals(result.getCode())) {
            throw new GatewayException("网关服务注册异常");
        }
    }
}
