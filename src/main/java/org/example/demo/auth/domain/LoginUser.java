package org.example.demo.auth.domain;

import lombok.Builder;
import lombok.Getter;
import org.example.demo.common.encrypt.service.port.PasswordEncoder;

@Getter
public class LoginUser {

    private String username;
    private String password;

    @Builder
    public LoginUser(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public boolean isCorrectPassword(PasswordEncoder passwordEncoder, String userPassword) {
        return passwordEncoder.encode(password).equals(userPassword);
    }
}
