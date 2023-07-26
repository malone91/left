package com.melo.gatewayassist;

import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSON;
import com.melo.gatewayassist.common.Result;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

public class ApiTest {

    @Test
    public void testRegisterGateway() {
        Map<String, Object> params = new HashMap<>();
        params.put("groupId", "102");
        params.put("gatewayId", "api-gateway-G7");
        params.put("gatewayName", "订单搜索网关");
        params.put("gatewayAddress", "127.0.0.1");
        String resultStr = HttpUtil.post("http://localhost:81/melo/admin/config/registerGateway", params, 3000);
        Result result = JSON.parseObject(resultStr, Result.class);
        System.out.println(result);
    }
}
