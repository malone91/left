package com.melo.gateway.core.session.defaults;

import com.melo.gateway.core.bind.IGenericReference;
import com.melo.gateway.core.executor.Executor;
import com.melo.gateway.core.mapping.HttpStatement;
import com.melo.gateway.core.session.Configuration;
import com.melo.gateway.core.session.GatewaySession;

import java.util.Map;

public class DefaultGatewaySession implements GatewaySession {

    private Configuration configuration;
    private String uri;
    private Executor executor;

    public DefaultGatewaySession(Configuration configuration, String uri, Executor executor) {
        this.configuration = configuration;
        this.uri = uri;
        this.executor = executor;
    }

    @Override
    public Object get(String methodName, Map<String, Object> params) {
        HttpStatement httpStatement = configuration.getHttpStatement(uri);
        try {
            return executor.exec(httpStatement, params);
        } catch (Exception e) {
            throw new RuntimeException("executor error cause ", e);
        }
    }

    @Override
    public Object post(String methodName, Map<String, Object> params) {
        return get(methodName, params);
    }

    @Override
    public IGenericReference getMapper() {
        return configuration.getMapper(uri, this);
    }

    @Override
    public Configuration getConfiguration() {
        return configuration;
    }
}
