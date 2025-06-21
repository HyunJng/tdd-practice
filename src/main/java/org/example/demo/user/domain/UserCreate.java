package org.example.demo.user.domain;

import lombok.Builder;
import lombok.Getter;
import org.example.demo.user.controller.dto.SignUp;
import org.example.demo.common.encrypt.service.port.PasswordEncoder;

@Getter
public class UserCreate {

    private final String username;

    private String password;

    @Builder
    public UserCreate(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public static UserCreate from(SignUp.Request request) {
        return UserCreate.builder()
                .username(request.getUsername())
                .password(request.getPassword())
                .build();
    }

    public void changePassword(PasswordEncoder passwordEncoder) {
        this.password = passwordEncoder.encode(this.password);
    }
}
