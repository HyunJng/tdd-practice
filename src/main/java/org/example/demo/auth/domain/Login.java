package org.example.demo.auth.domain;

import lombok.Builder;
import lombok.Getter;
import org.example.demo.auth.controller.dto.UserLoginDto;
import org.example.demo.common.encrypt.service.port.PasswordEncoder;

@Getter
public class Login {
    private String username;
    private String password;

    @Builder
    public Login(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public static Login from(UserLoginDto.Request request) {
        return Login.builder()
                .username(request.getUsername())
                .password(request.getPassword())
                .build();
    }

    public boolean isCorrectPassword(PasswordEncoder passwordEncoder, String userPassword) {
        return passwordEncoder.isMatcher(password, userPassword);
    }

}
