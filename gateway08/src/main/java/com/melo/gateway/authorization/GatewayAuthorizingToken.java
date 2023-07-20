package com.melo.gateway.authorization;

import org.apache.shiro.authc.AuthenticationToken;

public class GatewayAuthorizingToken implements AuthenticationToken {

    private static final long serialVersionUID = 1L;

    private String uId;
    private String jwt;

    public GatewayAuthorizingToken() {
    }

    public GatewayAuthorizingToken(String uId, String jwt) {
        this.uId = uId;
        this.jwt = jwt;
    }

    @Override
    public Object getPrincipal() {
        return uId;
    }

    @Override
    public Object getCredentials() {
        return this.jwt;
    }

    public String getJwt() {
        return jwt;
    }

    public void setJwt(String jwt) {
        this.jwt = jwt;
    }
}
