package org.example.demo.common.encrypt.service.port;

public interface PasswordEncoder {

    String encode(String password);

    boolean isMatcher(String rawPassword, String encodedPassword);
}
