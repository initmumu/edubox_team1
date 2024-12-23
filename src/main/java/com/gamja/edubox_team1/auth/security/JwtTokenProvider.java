package com.gamja.edubox_team1.auth.security;

import com.gamja.edubox_team1.auth.config.JwtProperties;
import com.gamja.edubox_team1.auth.dto.entity.AuthUser;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtTokenProvider {

    private final Key key;
    private final long ACCESS_TOKEN_DURATION;
    private final long REFRESH_TOKEN_DURATION;

    public JwtTokenProvider(JwtProperties jwtProperties) {
        this.key = Keys.hmacShaKeyFor(jwtProperties.getSecret().getBytes());
        this.ACCESS_TOKEN_DURATION = jwtProperties.getAccessTokenDuration();
        this.REFRESH_TOKEN_DURATION = jwtProperties.getRefreshTokenDuration();

    }

    public String generateAccessToken(AuthUser authUser) {
        return createToken(authUser, ACCESS_TOKEN_DURATION);
    }

    public String generateRefreshToken(AuthUser authUser) {
        return createToken(authUser, REFRESH_TOKEN_DURATION);
    }

    public String createToken(AuthUser authUser, long tokenExpiryTime) {
        Date now = new Date();
        Date validity = new Date(now.getTime() + tokenExpiryTime);

        return Jwts.builder()
                .setSubject(authUser.getEmail())
                .claim("role", authUser.getRole())
                .claim("id", authUser.getId())
                .setIssuedAt(now)
                .setExpiration(validity)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    public long getId(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
        return claims.get("id", Long.class); // 존재하지 않으면 null 반환
    }
}
