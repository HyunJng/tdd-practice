package org.example.demo.small.mock;

import org.example.demo.auth.service.port.JwtManager;
import org.example.demo.auth.service.port.TokenPayload;
import org.example.demo.user.domain.UserRole;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

public class FakeJwtManager implements JwtManager {

    private Map<String, String> tokens = new HashMap<>();
    private AtomicLong atomicLong = new AtomicLong();

    @Override
    public String createToken(long userId, String username, List<UserRole> roles) {
        String token = atomicLong.incrementAndGet() + "_" + userId;
        String roleInfo = roles.stream().map(userRole -> userRole.name()).collect(Collectors.joining("^"));
        String userInfo = userId + ":" + username + ":" + roleInfo;
        tokens.put(token, userInfo);

        return token;
    }

    @Override
    public boolean validateToken(String token) {
        if (tokens.containsKey(token) && token.contains("_")) {
            String userId = token.split("_")[1];
            return tokens.get(token).split(":")[0].equals(userId);
        }
        return false;
    }

    @Override
    public TokenPayload parseClaims(String token) {
        String[] userInfo = tokens.get(token).split(":");
        String[] rolesArr = userInfo[2].split("^");
        return TokenPayload.builder()
                .id(Long.parseLong(userInfo[0]))
                .usename(userInfo[1])
                .roles(List.of(rolesArr))
                .build();
    }
}
