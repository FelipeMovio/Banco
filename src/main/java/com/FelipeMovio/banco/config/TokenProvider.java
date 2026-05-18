package com.FelipeMovio.banco.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class TokenProvider {

    @Value("${JWT_EXPIRATION}")
    private Long expiratioTime;

    @Value("${JWT_KEY}")
    private String key;

    // gerar um token
    public String generateToken(Authentication authentication) {
        UserDetails user = (UserDetails) authentication.getPrincipal();
        return buildToken(user.getUsername());
    }

    private String buildToken(String username) {
        Date now = new Date();
        Date expiration = new Date(now.getTime() + expiratioTime);
        return Jwts.builder()
                .subject(username)
                .issuedAt(now)
                .expiration(expiration)
                .signWith(getSigningKey())
                .compact();
    }

    private SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(key.getBytes());
    }

    // validar um token
    public boolean isTokenValid(String token) {
        try {
            getClaimsFromToken(token);
            return true;
        }catch (Exception e){
            return false;
        }

    }

    // extrair informações do token

    public String getUsernameFromToken(String token) {
        return getClaimsFromToken(token).getSubject();
    }


    private Claims getClaimsFromToken(String token) {
        //validar assinatura
        //validar expiracao
        return Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
}

