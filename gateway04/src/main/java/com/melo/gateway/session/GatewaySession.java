package com.melo.gateway.session;


import com.melo.gateway.bind.IGenericReference;

public interface GatewaySession {

    Object get(String method, Object parameter);

    IGenericReference getMapper();

    Configuration getConfiguration();
}
