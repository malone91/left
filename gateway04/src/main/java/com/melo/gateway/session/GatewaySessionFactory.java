package com.melo.gateway.session;

public interface GatewaySessionFactory {

    GatewaySession openSession(String uri);
}
