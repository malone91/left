package com.melo.gateway.datasource.connection;

import com.melo.gateway.datasource.Connection;
import org.apache.dubbo.config.ApplicationConfig;
import org.apache.dubbo.config.ReferenceConfig;
import org.apache.dubbo.config.RegistryConfig;
import org.apache.dubbo.config.bootstrap.DubboBootstrap;
import org.apache.dubbo.rpc.service.GenericService;

public class DubboConnection implements Connection {

    private final GenericService genericService;

    public DubboConnection(ApplicationConfig applicationConfig, RegistryConfig registryConfig, ReferenceConfig<GenericService> referenceConfig) {
        DubboBootstrap bootstrap = DubboBootstrap.getInstance()
                .application(applicationConfig)
                .registry(registryConfig)
                .reference(referenceConfig)
                .start();
        this.genericService = bootstrap.getCache().get(referenceConfig);
    }

    @Override
    public Object execute(String method, String[] parameterTypes, String[] parameterNames, Object[] args) {
        return genericService.$invoke(method, parameterTypes, args);
    }
}
