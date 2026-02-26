package net.jrz.tlias.utils;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;
import java.util.Map;

public class JwtUtils {
    private static final String signKey = "SVRIRUlNQQ==";
    private static final Long expire = 43200000L;

    /*
    * 生成JWT令牌
    * */
    public static String generateJwt(Map<String, Object> claims){
        return Jwts.builder().addClaims(claims).signWith(SignatureAlgorithm.HS256, signKey).setExpiration(new Date(System.currentTimeMillis() + expire)).compact();
    }

    /*
    * 解析JWT令牌
    * */
    public static void parseJwt(String jwt){
        Jwts.parser().setSigningKey(signKey).parseClaimsJws(jwt).getBody();
    }

}
