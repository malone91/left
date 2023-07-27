package com.melo.gateway.core.session;

import com.melo.gateway.core.authorization.IAuth;
import com.melo.gateway.core.authorization.auth.AuthService;
import com.melo.gateway.core.bind.IGenericReference;
import com.melo.gateway.core.bind.MapperRegistry;
import com.melo.gateway.core.datasource.Connection;
import com.melo.gateway.core.executor.Executor;
import com.melo.gateway.core.executor.SimpleExecutor;
import com.melo.gateway.core.mapping.HttpStatement;
import org.apache.dubbo.config.ApplicationConfig;
import org.apache.dubbo.config.ReferenceConfig;
import org.apache.dubbo.config.RegistryConfig;
import org.apache.dubbo.rpc.service.GenericService;

import java.util.HashMap;
import java.util.Map;

public class Configuration {

    // 网关 Netty 服务地址
    private String hostName = "127.0.0.1";
    // 网关 Netty 服务端口
    private int port = 7397;
    // 网关 Netty 服务线程数配置
    private int bossNThreads = 1;
    private int workNThreads = 4;

    private final MapperRegistry mapperRegistry = new MapperRegistry(this);
    private final Map<String, HttpStatement> httpStatementMap = new HashMap<>();
    private final Map<String, ApplicationConfig> applicationConfigMap = new HashMap<>();
    private final Map<String, RegistryConfig> registryConfigMap = new HashMap<>();
    private final Map<String, ReferenceConfig<GenericService>> referenceConfigMap = new HashMap<>();
    private final IAuth auth = new AuthService();

    public String getHostName() {
        return hostName;
    }

    public void setHostName(String hostName) {
        this.hostName = hostName;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public int getBossNThreads() {
        return bossNThreads;
    }

    public void setBossNThreads(int bossNThreads) {
        this.bossNThreads = bossNThreads;
    }

    public int getWorkNThreads() {
        return workNThreads;
    }

    public void setWorkNThreads(int workNThreads) {
        this.workNThreads = workNThreads;
    }

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
