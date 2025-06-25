package org.example.demo.auth.infrastructure.security;

import org.example.demo.auth.service.port.TokenPayload;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class CustomUserDetails implements UserDetails {

    private final Long id;
    private final String username;
    private final List<GrantedAuthority> authorities;

    public CustomUserDetails(TokenPayload tokenPayload) {
        this.id = tokenPayload.getId();
        this.username = tokenPayload.getUsename();
        this.authorities = tokenPayload.getRoles().stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }

    public Long getId() {
        return id;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return null;
    }
}
