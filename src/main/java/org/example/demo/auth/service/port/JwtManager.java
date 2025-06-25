package org.example.demo.auth.service.port;

import org.example.demo.user.domain.UserRole;

import java.util.List;

public interface JwtManager {

    String createToken(long userId, String username, List<UserRole> roles);

    boolean validateToken(String token);

    TokenPayload parseClaims(String token);
}
