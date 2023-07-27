package com.melo.gateway.core.session;

public interface GatewaySessionFactory {

    GatewaySession openSession(String uri);
}
