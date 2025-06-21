package org.example.demo.auth.domain;

import lombok.Builder;
import lombok.Getter;
import org.example.demo.auth.controller.dto.UserLoginDto;
import org.example.demo.common.encrypt.service.port.PasswordEncoder;

@Getter
public class UserLogin {

    private String username;
    private String password;

    @Builder
    public UserLogin(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public static UserLogin from(UserLoginDto.Request request) {
        return UserLogin.builder()
                .username(request.getUsername())
                .password(request.getPassword())
                .build();
    }

    public boolean isCorrectPassword(PasswordEncoder passwordEncoder, String userPassword) {
        return passwordEncoder.isMatcher(userPassword, password);
    }
}
