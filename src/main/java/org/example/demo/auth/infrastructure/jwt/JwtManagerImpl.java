package org.example.demo.auth.infrastructure.jwt;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import org.example.demo.auth.service.port.JwtManager;
import org.example.demo.auth.service.port.TokenPayload;
import org.example.demo.common.exception.domain.ErrorCode;
import org.example.demo.common.time.port.DateHolder;
import org.example.demo.user.domain.UserRole;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Base64;
import java.util.Date;
import java.util.List;

@Slf4j
@Component
public class JwtManagerImpl implements JwtManager {
    private static final long TOKEN_TIME = 60 * 60 * 1000L;

    private final Key key;
    private final SignatureAlgorithm signatureAlgorithm;
    private final DateHolder dateHolder;
    private final JwtProperties jwtProperties;

    @Builder
    public JwtManagerImpl(DateHolder dateHolder, JwtProperties jwtProperties) {
        this.dateHolder = dateHolder;
        this.jwtProperties = jwtProperties;
        this.signatureAlgorithm = SignatureAlgorithm.HS256;
        this.key = setKey();
    }

    private Key setKey() {
        byte[] bytes = Base64.getDecoder().decode(jwtProperties.getKey());
        return Keys.hmacShaKeyFor(bytes);
    }

    @Override
    public String createToken(long userId, String username, List<UserRole> roles) {
        long now = dateHolder.nowEpochTime();
        Claims claims = Jwts.claims().setSubject(username);
        claims.put("userId", userId);
        claims.put("roles", roles);

        return Jwts.builder()
                        .setClaims(claims)
                        .setExpiration(new Date(now + TOKEN_TIME))
                        .setIssuedAt(new Date(now))
                        .signWith(key, signatureAlgorithm)
                        .compact();
    }

    @Override
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (SecurityException | MalformedJwtException e) {
            log.debug(ErrorCode.INVALID_RESOURCES.getMessage("JWT 서명"));
        } catch (ExpiredJwtException e) {
            log.debug(ErrorCode.EXPIRES_RESOURCES.getMessage("JWT token"));
        } catch (UnsupportedJwtException e) {
            log.debug(ErrorCode.UNSUPPORTED_RESOURCES.getMessage("JWT token"));
        } catch (IllegalArgumentException e) {
            log.debug(ErrorCode.WRONG_RESOURCES.getMessage("JWT token"));
        }
        return false;
    }

    @Override
    public TokenPayload parseClaims(String token) {
        Claims claims = Jwts.parserBuilder().setSigningKey(key).build()
                .parseClaimsJws(token)
                .getBody();

        return TokenPayload.builder()
                .id(claims.get("userId", Long.class))
                .usename(claims.getSubject())
                .roles(claims.get("roles", List.class))
                .build();
    }
}
