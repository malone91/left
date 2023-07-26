package com.melo.gatewaycenter;

import com.melo.gatewaycenter.application.IConfigManageService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

@SpringBootTest
@RunWith(SpringRunner.class)
public class ApiTest {

    @Resource
    private IConfigManageService configManageService;

    @Test
    public void testRegister() {
        configManageService.registerGatewayServerNode("101", "api-gateway-1", "订单网关", "127.0.0.1");
        configManageService.registerGatewayServerNode("102", "api-gateway-2", "订单网关", "127.0.0.2");
        configManageService.registerGatewayServerNode("103", "api-gateway-3", "订单网关", "127.0.0.3");
    }
}
