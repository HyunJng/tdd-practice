package org.example.demo.auth.infrastructure.jwt;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.Builder;
import org.example.demo.common.exception.domain.CommonException;
import org.example.demo.common.exception.domain.ErrorCode;
import org.example.demo.common.time.port.DateHolder;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Base64;
import java.util.Date;

@Component
public class JwtManager {
    private static final long TOKEN_TIME = 60 * 60 * 1000L;

    private final Key key;
    private final SignatureAlgorithm signatureAlgorithm;
    private final DateHolder dateHolder;
    private final JwtProperties jwtProperties;

    @Builder
    public JwtManager(DateHolder dateHolder, JwtProperties jwtProperties) {
        this.dateHolder = dateHolder;
        this.jwtProperties = jwtProperties;
        this.signatureAlgorithm = SignatureAlgorithm.HS256;
        this.key = setKey();
    }

    private Key setKey() {
        byte[] bytes = Base64.getDecoder().decode(jwtProperties.getKey());
        return Keys.hmacShaKeyFor(bytes);
    }

    public String createToken(long userId) {
        long now = dateHolder.nowEpochTime();

        return Jwts.builder()
                        .setSubject(String.valueOf(userId))
                        .setExpiration(new Date(now + TOKEN_TIME))
                        .setIssuedAt(new Date(now))
                        .signWith(key, signatureAlgorithm)
                        .compact();
    }

    public void validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
        } catch (SecurityException | MalformedJwtException e) {
            throw new CommonException(ErrorCode.INVALID_RESOURCES, "JWT 서명");
        } catch (ExpiredJwtException e) {
            throw new CommonException(ErrorCode.EXPIRES_RESOURCES, "JWT token");
        } catch (UnsupportedJwtException e) {
            throw new CommonException(ErrorCode.UNSUPPORTED_RESOURCES, "JWT token");
        } catch (IllegalArgumentException e) {
            throw new CommonException(ErrorCode.WRONG_RESOURCES, "JWT token");
        }
    }

    public long getUserIdFromToken(String token) {
        String subject = Jwts.parserBuilder().setSigningKey(key).build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
        return Long.parseLong(subject);
    }
}
