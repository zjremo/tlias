package net.jrz.tlias;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.junit.jupiter.api.Test;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class JwtTest {
    @Test
    public void testGenJwt() {
        Map<String, Object> claims = new HashMap<>();
        claims.put("id", 10);
        claims.put("username", "tom");

        // 有效期设置为12个小时 aXRjYXN0为签名密钥
        String jwt = Jwts.builder().signWith(SignatureAlgorithm.HS256, "aXRjYXN0").addClaims(claims).setExpiration(new Date(System.currentTimeMillis() + 12 * 3600 * 1000)).compact();
        System.out.println(jwt);
    }

    @Test
    public void testParseJwt() {
        // "aXRjYXN0"设置为签名密钥
        Claims claims = Jwts.parser().setSigningKey("aXRjYXN0").parseClaimsJws("eyJhbGciOiJIUzI1NiJ9.eyJpZCI6MTAsInVzZXJuYW1lIjoidG9tIiwiZXhwIjoxNzcyMTE1ODMwfQ.MXV3zeNGl7MHmhhJhCrk_GVfQ5HgeMaMI2EKf-8fMfk").getBody();
        System.out.println(claims);
    }
}
