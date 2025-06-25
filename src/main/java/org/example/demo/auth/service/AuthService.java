package org.example.demo.auth.service;

import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.example.demo.auth.domain.UserLogin;
import org.example.demo.auth.service.port.JwtManager;
import org.example.demo.common.encrypt.service.port.PasswordEncoder;
import org.example.demo.common.exception.domain.CommonException;
import org.example.demo.common.exception.domain.ErrorCode;
import org.example.demo.user.domain.User;
import org.example.demo.user.service.port.UserRepository;
import org.springframework.stereotype.Service;

@Builder
@RequiredArgsConstructor
@Service
public class AuthService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final JwtManager jwtManager;

    public String login(UserLogin userLogin) {
        User user = userRepository.findByUsername(userLogin.getUsername())
                .orElseThrow(() -> new CommonException(ErrorCode.RESOURCE_NOT_FOUND, "user"));
        if (!userLogin.isCorrectPassword(passwordEncoder, user.getPassword())) {
            throw new CommonException(ErrorCode.WRONG_REQUEST_PARAM_DATA, "password");
        }
        return  jwtManager.createToken(user.getId());
    }
}
