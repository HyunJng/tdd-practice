package org.example.demo.common.encrypt.infrastructure;

import org.example.demo.common.encrypt.service.port.PasswordEncoder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class PasswordEncoderImpl implements PasswordEncoder {

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public PasswordEncoderImpl() {
        this.bCryptPasswordEncoder = new BCryptPasswordEncoder();
    }

    @Override
    public String encode(String password) {
        return bCryptPasswordEncoder.encode(password);
    }

    @Override
    public boolean isMatcher(String password, String target) {
        return bCryptPasswordEncoder.matches(password, target);
    }
}
