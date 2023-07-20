package com.melo.gateway.session;

import com.melo.gateway.authorization.IAuth;
import com.melo.gateway.authorization.auth.AuthService;
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
    private final IAuth auth = new AuthService();


    public Configuration() {
    }

    public synchronized void registerConfig(String applicationName, String address, String interfaceName, String version) {
        if (applicationConfigMap.get(applicationName) == null) {
            ApplicationConfig applicationConfig = new ApplicationConfig();
            applicationConfig.setName(applicationName);
            applicationConfig.setQosEnable(false);
            applicationConfigMap.put(applicationName, applicationConfig);
        }

        if (registryConfigMap.get(applicationName) == null) {
            RegistryConfig registryConfig = new RegistryConfig();
            registryConfig.setAddress(address);
            registryConfig.setRegister(false);
            registryConfigMap.put(applicationName, registryConfig);
        }

        if (referenceConfigMap.get(interfaceName) == null) {
            ReferenceConfig<GenericService> referenceConfig = new ReferenceConfig<>();
            referenceConfig.setInterface(interfaceName);
            referenceConfig.setVersion(version);
            referenceConfig.setGeneric("true");
            referenceConfigMap.put(interfaceName, referenceConfig);
        }
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

    public boolean authValidate(String uId, String token) {
        return auth.validate(uId, token);
    }
}
