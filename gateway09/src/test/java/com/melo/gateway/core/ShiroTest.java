package com.melo.gateway.core;

import com.melo.gateway.core.authorization.IAuth;
import com.melo.gateway.core.authorization.JwtUtil;
import com.melo.gateway.core.authorization.auth.AuthService;
import io.jsonwebtoken.Claims;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

public class ShiroTest {

    @Test
    public void testJwt() {
        String issuer = "melo";
        long ttlMillis = 7 * 24 * 60 * 60 * 1000L;
        Map<String, Object> claims = new HashMap<>();
        claims.put("key", "pela");

        String token = JwtUtil.encode(issuer, ttlMillis, claims);
        //eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJtZWxvIiwiZXhwIjoxNjkwMzc0MDc3LCJpYXQiOjE2ODk3NjkyNzcsImtleSI6InBlbGEifQ.6cZB7prTiXOKt3n9CqsaIXYvf3SzUrh7abrzzBHYh6I
        System.out.println(token);

        Claims parser = JwtUtil.decode(token);
        //melo
        System.out.println(parser.getSubject());
    }

    @Test
    public void testAuthService() {
        IAuth auth = new AuthService();
        //参数来自第一个test
        boolean melo = auth.validate("melo", "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJtZWxvIiwiZXhwIjoxNjkwMzc0MDc3LCJpYXQiOjE2ODk3NjkyNzcsImtleSI6InBlbGEifQ.6cZB7prTiXOKt3n9CqsaIXYvf3SzUrh7abrzzBHYh6I");
        //true
        System.out.println(melo);
    }

}
