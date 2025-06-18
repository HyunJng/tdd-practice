package org.example.demo.user.domain;

import lombok.Builder;
import lombok.Getter;
import org.example.demo.common.service.port.DateHolder;

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

    public static User from(UserCreate userCreate, DateHolder dateHolder) {
        return User.builder()
                .username(userCreate.getUsername())
                .password(userCreate.getPassword())
                .createAt(dateHolder.now())
                .build();
    }
}
