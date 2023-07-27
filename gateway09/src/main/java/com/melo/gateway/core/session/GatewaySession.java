package com.melo.gateway.core.session;

import com.melo.gateway.core.bind.IGenericReference;

import java.util.Map;

public interface GatewaySession {

    Object get(String methodName, Map<String, Object> params);

    Object post(String methodName, Map<String, Object> params);

    IGenericReference getMapper();

    Configuration getConfiguration();
}
