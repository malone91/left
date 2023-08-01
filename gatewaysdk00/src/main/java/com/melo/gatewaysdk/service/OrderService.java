package com.melo.gatewaysdk.service;

import com.melo.gatewaysdk.annotation.ApiProducerClazz;
import com.melo.gatewaysdk.annotation.ApiProducerMethod;
import org.springframework.stereotype.Service;

@Service
@ApiProducerClazz(interfaceName = "订单", interfaceVersion = "1.0.0")
public class OrderService {

    @ApiProducerMethod(methodName = "详情", uri = "/melo/order/detail", httpCommandType = "POST", auth = 1)
    public String detail(String no) {
        return "hi" + no + " by annotation";
    }
}
