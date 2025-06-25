package org.example.demo.auth.service.port;

import lombok.Builder;

import java.util.List;

public class TokenPayload {
    private final Long id;
    private final String usename;
    private final List<String> roles;

    @Builder
    public TokenPayload(Long id, String usename, List<String> roles) {
        this.id = id;
        this.usename = usename;
        this.roles = roles;
    }

    public Long getId() {
        return id;
    }

    public String getUsename() {
        return usename;
    }

    public List<String> getRoles() {
        return roles;
    }
}
