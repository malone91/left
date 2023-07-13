package com.melo.gateway.bind;

import com.melo.gateway.session.Configuration;
import org.apache.dubbo.config.ApplicationConfig;
import org.apache.dubbo.config.ReferenceConfig;
import org.apache.dubbo.config.RegistryConfig;
import org.apache.dubbo.config.bootstrap.DubboBootstrap;
import org.apache.dubbo.config.utils.ReferenceConfigCache;
import org.apache.dubbo.rpc.service.GenericService;

import java.util.HashMap;
import java.util.Map;

public class GenericReferenceRegistry {

    private final Configuration configuration;

    public GenericReferenceRegistry(Configuration configuration) {
        this.configuration = configuration;
    }

    private final Map<String, GenericReferenceProxyFactory> knownGenericReferences = new HashMap<>();

    public IGenericReference getGenericReference(String methodName) {
        GenericReferenceProxyFactory proxyFactory = knownGenericReferences.get(methodName);
        if (proxyFactory == null) {
            throw new RuntimeException("type" + methodName + " is not known to the registry");
        }
        return proxyFactory.newInstance(methodName);
    }

    public void addGenericReference(String application, String interfaceName, String methodName) {
        ApplicationConfig applicationConfig = configuration.getApplicationConfig(application);
        RegistryConfig registryConfig = configuration.getRegistryConfig(application);
        ReferenceConfig<GenericService> referenceConfig = configuration.getReferenceConfig(interfaceName);
        DubboBootstrap dubboBootstrap = DubboBootstrap.getInstance();
        dubboBootstrap.application(applicationConfig).registry(registryConfig).reference(referenceConfig).start();
        GenericService genericService = ReferenceConfigCache.getCache().get(referenceConfig);
        knownGenericReferences.put(methodName, new GenericReferenceProxyFactory(genericService));
    }
}
