package com.melo.session;

import com.melo.gateway.IGenericReference;

public interface GatewaySession {

    Object get(String uri, Object parameter);

    IGenericReference getMapper(String uri);

    Configuration getConfiguration();
}
