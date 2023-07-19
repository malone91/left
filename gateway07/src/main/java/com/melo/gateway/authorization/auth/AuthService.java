package com.melo.gateway.authorization.auth;

import com.melo.gateway.authorization.GatewayAuthorizingToken;
import com.melo.gateway.authorization.IAuth;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.Factory;

public class AuthService implements IAuth {

    private Subject subject;

    public AuthService() {
        Factory<SecurityManager> factory = new IniSecurityManagerFactory("classpath:shiro.ini");
        SecurityManager securityManager = factory.getInstance();
        SecurityUtils.setSecurityManager(securityManager);
        this.subject = SecurityUtils.getSubject();
    }

    @Override
    public boolean validate(String id, String token) {
        //身份认证
        try {
            subject.login(new GatewayAuthorizingToken(id, token));
            return subject.isAuthenticated();
        } finally {
            subject.logout();
        }
    }
}
