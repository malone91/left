package com.melo.gateway;

import org.apache.dubbo.config.ApplicationConfig;
import org.apache.dubbo.config.ReferenceConfig;
import org.apache.dubbo.config.RegistryConfig;
import org.apache.dubbo.config.bootstrap.DubboBootstrap;
import org.apache.dubbo.config.utils.ReferenceConfigCache;
import org.apache.dubbo.rpc.service.GenericService;
import org.junit.Test;

public class RpcTest {

    @Test
    public void testRpc() {
        String appName = "api-gateway-test";
        ApplicationConfig applicationConfig = new ApplicationConfig();
        applicationConfig.setName(appName);
        applicationConfig.setQosEnable(true);

        RegistryConfig registryConfig = new RegistryConfig();
        registryConfig.setAddress("zookeeper://127.0.0.1:2181");
        registryConfig.setRegister(false);

        ReferenceConfig<GenericService> referenceConfig = new ReferenceConfig<>();
        referenceConfig.setInterface("com.melo.rpc.HelloService");
        referenceConfig.setVersion("1.0.0");
        referenceConfig.setGeneric("true");

        DubboBootstrap.getInstance()
                .application(applicationConfig)
                .registry(registryConfig)
                .reference(referenceConfig)
                .start();

        GenericService genericService = ReferenceConfigCache.getCache().get(referenceConfig);
        Object result = genericService.$invoke("hello", new String[]{"java.lang.String"}, new Object[]{"around"});
        System.out.println(result);

    }
}
