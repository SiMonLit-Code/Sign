package com.sign.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * @author : czz
 * @version : 1.0.0
 * @create : 2020-12-29 15:16:00
 * @description : jwt生成token
 */
@Component
public class JwtTokenUtils {
    private static String secret ;

    private static long expiration;

    @Value("${jwt.secret}")
    public void setSecret(String secret) {
        JwtTokenUtils.secret = secret;
    }

    @Value("${jwt.validate}")
    public void setExpiration(long expiration) {
        JwtTokenUtils.expiration = expiration;
    }

    /**
     * 将从Token中Subject
     * @param token
     * @return
     */
    public static String getNameFromToken(String token){
        return getClaimFromToken(token, Claims::getSubject);
    }

    private static <T> T getClaimFromToken(String token, Function<Claims,T> claimsTFunction){
        Claims claims = AllClaimFromToken(token);
        return claimsTFunction.apply(claims);
    }

    private static Claims AllClaimFromToken(String token){
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
    }

    public static String generateToken(String account){
        Map<String,Object> claim = new HashMap<>();
        return doGenerateToken(claim,account);
    }

    private static String doGenerateToken(Map<String,Object> claim, String account){
        return Jwts.builder()
                .setClaims(claim)
                .setSubject(account)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis()+expiration))
                .signWith(SignatureAlgorithm.HS256,secret)
                .compact();
    }

    /**
     * 校验token
     * @return
     */
    public static boolean validateToken(String token, UserDetails userDetails){
        String account = getNameFromToken(token);
        return account.equals(userDetails.getUsername()) && !isTokenExpired(token);
    }

    /**
     * 校验token是否过期
     * @param token
     * @return
     */
    private static boolean isTokenExpired(String token){
        Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    /**
     * 将从Token中ExpirationDate
     * @param token
     * @return
     */
    private static Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }

}
