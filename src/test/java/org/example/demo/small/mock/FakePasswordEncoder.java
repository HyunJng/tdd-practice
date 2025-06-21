package org.example.demo.small.mock;

import org.example.demo.common.encrypt.service.port.PasswordEncoder;

public class FakePasswordEncoder implements PasswordEncoder {

    @Override
    public String encode(String password) {
        return "encoded:" + password;
    }

    @Override
    public boolean isMatcher(String rawPassword, String encodedPassword) {
        return encodedPassword.equals("encoded:" + rawPassword);
    }
}
