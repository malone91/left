package com.melo.gateway.authorization;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class JwtUtil {

    public static final String signingKey = "B*B^5Fe";

    public static String encode(String issuer, long ttlMillis, Map<String, Object> claims) {
        if (claims == null) {
            claims = new HashMap<>();
        }
        long nowMillis = System.currentTimeMillis();
        Date date = new Date(nowMillis);

        JwtBuilder jwtBuilder = Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(date)
                .setSubject(issuer)
                .signWith(SignatureAlgorithm.HS256, signingKey);
        if (ttlMillis >= 0) {
            long expMillis = nowMillis + ttlMillis;
            Date expDate = new Date((expMillis));
            jwtBuilder.setExpiration(expDate);
        }

        return jwtBuilder.compact();
    }

    public static Claims decode(String token) {
        return Jwts.parser()
                .setSigningKey(signingKey)
                .parseClaimsJws(token)
                .getBody();
    }
}
