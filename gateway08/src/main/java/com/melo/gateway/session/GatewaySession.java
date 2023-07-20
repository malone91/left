package com.melo.gateway.session;

import com.melo.gateway.bind.IGenericReference;

import java.util.Map;

public interface GatewaySession {

    Object get(String methodName, Map<String, Object> params);

    Object post(String methodName, Map<String, Object> params);

    IGenericReference getMapper();

    Configuration getConfiguration();
}
