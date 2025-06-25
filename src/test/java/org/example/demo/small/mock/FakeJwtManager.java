package org.example.demo.small.mock;

import org.example.demo.auth.service.port.JwtManager;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

public class FakeJwtManager implements JwtManager {

    private Map<String, Long> tokens = new HashMap<>();
    private AtomicLong atomicLong = new AtomicLong();

    @Override
    public String createToken(long userId) {
        String token = atomicLong.incrementAndGet() + "_" + userId;
        tokens.put(token, userId);

        return token;
    }

    @Override
    public boolean validateToken(String token) {
        if (tokens.containsKey(token) && token.contains("_")) {
            long userId = Long.valueOf(token.split("_")[1]);
            return tokens.get(token).equals(userId);
        }
        return false;
    }

    @Override
    public long getUserIdFromToken(String token) {
        return tokens.get(token);
    }
}
