package com.whiz.app.boot.infrastructure.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

@Slf4j
@Component
public class TokenProvider implements InitializingBean {

    private static final String AUTHORITIES_KEY = "auth";

    private final String base64Secret;
    private final long tokenValidityInMilliseconds;
    private final long tokenValidityInMillisecondsForRememberMe;

    private Key key;

    public TokenProvider(
        @Value("${jwt.base64-secret}") String base64Secret,
        @Value("${jwt.token-validity-in-seconds}") long tokenValidityInSeconds,
        @Value("${jwt.token-validity-in-seconds-for-remember-me}") long tokenValidityInSecondsForRememberMe) {
        this.base64Secret = base64Secret;
        this.tokenValidityInMilliseconds = tokenValidityInSeconds * 1000;
        this.tokenValidityInMillisecondsForRememberMe = tokenValidityInSecondsForRememberMe * 1000;
    }

    @Override
    public void afterPropertiesSet() {
        byte[] keyBytes = Decoders.BASE64.decode(base64Secret);
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }

    public String createToken(Authentication authentication, boolean rememberMe) {
        String authorities = authentication.getAuthorities()
            .stream()
            .map(GrantedAuthority::getAuthority)
            .collect(Collectors.joining(","));
        long now = (new Date()).getTime();
        Date validity = (rememberMe) ? new Date(now + this.tokenValidityInMillisecondsForRememberMe)
            : new Date(now + this.tokenValidityInMilliseconds);
        return Jwts.builder()
            .setSubject(authentication.getName())
            .claim(AUTHORITIES_KEY, authorities)
            .signWith(key, SignatureAlgorithm.HS512)
            .setExpiration(validity)
            .compact();
    }

    public Authentication getAuthentication(String token) {
        Claims claims = Jwts.parser()
            .setSigningKey(key)
            .parseClaimsJws(token)
            .getBody();
        String auth = claims.get(AUTHORITIES_KEY).toString();
        Collection<? extends GrantedAuthority> authorities = Arrays.stream(auth.split(","))
            .map(SimpleGrantedAuthority::new)
            .collect(Collectors.toList());
        User principal = new User(claims.getSubject(), "", authorities);
        return new UsernamePasswordAuthenticationToken(principal, token, authorities);
    }

    public boolean validateToken(String authToken) {
        try {
            Jwts.parser().setSigningKey(key).parseClaimsJws(authToken);
            return true;
        } catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
            log.info("Invalid JWT signature trace: {}", e.getMessage());
        } catch (ExpiredJwtException e) {
            log.info("Expired JWT token trace: {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            log.info("Unsupported JWT token trace: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            log.info("JWT token compact of handler are invalid trace: {}", e.getMessage());
        }
        return false;
    }
}