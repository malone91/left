package com.melo.gateway;

import org.apache.dubbo.config.ApplicationConfig;
import org.apache.dubbo.config.ReferenceConfig;
import org.apache.dubbo.config.RegistryConfig;
import org.apache.dubbo.config.bootstrap.DubboBootstrap;
import org.apache.dubbo.config.utils.ReferenceConfigCache;
import org.apache.dubbo.rpc.service.GenericService;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

public class RpcTest {

    /**
     * 原始调用 初步用泛化调用的方式，验证dubbo在zk的注册服务是否可用
     */
    @Test
    public void testRpc() {
        ApplicationConfig applicationConfig = new ApplicationConfig();
        applicationConfig.setName("api-gateway-test");
        applicationConfig.setQosEnable(false);

        RegistryConfig registryConfig = new RegistryConfig();
        registryConfig.setAddress("zookeeper://127.0.0.1:2181");
        registryConfig.setRegister(false);

        ReferenceConfig<GenericService> referenceConfig = new ReferenceConfig<>();
        referenceConfig.setInterface("com.melo.rpc.HelloService");
        referenceConfig.setVersion("1.0.0");
        referenceConfig.setGeneric("true");

        DubboBootstrap bootstrap = DubboBootstrap.getInstance()
                .application(applicationConfig)
                .registry(registryConfig)
                .reference(referenceConfig)
                .start();

        GenericService genericService = ReferenceConfigCache.getCache().get(referenceConfig);


        Map<String, Object> param01 = new HashMap<>();
        param01.put("str", "01");

        Map<String, Object> param02 = new HashMap<>();
        param02.put("uid", "pela");
        param02.put("name", "melo");

        String[] parameterTypes = new String[]{"java.lang.String", "com.melo.dto.XReq"};
        Object result = genericService.$invoke("addPlus", parameterTypes, new Object[]{param01.values().toArray()[0], param02});
        System.out.println(result);

        Object helloResult = genericService.$invoke("hello", new String[]{"java.lang.String"},
                new Object[]{"a"});
        System.out.println(helloResult);
    }
}
