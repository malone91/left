package com.melo.gateway.session;

import com.melo.gateway.bind.IGenericReference;
import com.melo.gateway.bind.MapperRegistry;
import com.melo.gateway.datasource.Connection;
import com.melo.gateway.executor.Executor;
import com.melo.gateway.executor.SimpleExecutor;
import com.melo.gateway.mapping.HttpStatement;
import org.apache.dubbo.config.ApplicationConfig;
import org.apache.dubbo.config.ReferenceConfig;
import org.apache.dubbo.config.RegistryConfig;
import org.apache.dubbo.rpc.service.GenericService;

import java.util.HashMap;
import java.util.Map;

public class Configuration {

    private final MapperRegistry mapperRegistry = new MapperRegistry(this);
    private final Map<String, HttpStatement> httpStatementMap = new HashMap<>();
    private final Map<String, ApplicationConfig> applicationConfigMap = new HashMap<>();
    private final Map<String, RegistryConfig> registryConfigMap = new HashMap<>();
    private final Map<String, ReferenceConfig<GenericService>> referenceConfigMap = new HashMap<>();

    public Configuration() {
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

        applicationConfigMap.put(appName, applicationConfig);
        registryConfigMap.put(appName, registryConfig);
        referenceConfigMap.put("com.melo.rpc.HelloService", referenceConfig);
    }

    public ApplicationConfig getApplicationConfig(String applicationName) {
        return applicationConfigMap.get(applicationName);
    }

    public RegistryConfig getRegistryConfig(String applicationName) {
        return registryConfigMap.get(applicationName);
    }

    public ReferenceConfig<GenericService> getReferenceConfig(String interfaceName) {
        return referenceConfigMap.get(interfaceName);
    }

    public void addMapper(HttpStatement statement) {
        mapperRegistry.addMapper(statement);
    }

    public IGenericReference getMapper(String uri, GatewaySession gatewaySession) {
        return mapperRegistry.getMapper(uri, gatewaySession);
    }

    public void addHttpStatement(HttpStatement statement) {
        httpStatementMap.put(statement.getUri(), statement);
    }

    public HttpStatement getHttpStatement(String uri) {
        return httpStatementMap.get(uri);
    }

    public Executor newExecutor(Connection connection) {
        return new SimpleExecutor(this, connection);
    }
}
