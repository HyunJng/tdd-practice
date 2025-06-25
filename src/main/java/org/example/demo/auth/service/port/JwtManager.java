package org.example.demo.auth.service.port;

public interface JwtManager {

    String createToken(long userId);

    boolean validateToken(String token);

    long getUserIdFromToken(String token);
}
