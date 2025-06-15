package org.example.demo.user.domain;

import lombok.Builder;
import lombok.Getter;

@Getter
public class User {

    private final Long id;

    private final String username;

    private final String password;

    private final String createAt;

    @Builder
    public User(Long id, String username, String password, String createAt) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.createAt = createAt;
    }
}
