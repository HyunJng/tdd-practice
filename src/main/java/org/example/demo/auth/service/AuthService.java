package org.example.demo.auth.service;

import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.example.demo.auth.domain.Login;
import org.example.demo.auth.service.port.JwtManager;
import org.example.demo.common.encrypt.service.port.PasswordEncoder;
import org.example.demo.common.exception.domain.CommonException;
import org.example.demo.common.exception.domain.ErrorCode;
import org.example.demo.user.domain.User;
import org.example.demo.user.domain.UserRole;
import org.example.demo.user.service.port.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Builder
@RequiredArgsConstructor
@Service
public class AuthService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final JwtManager jwtManager;

    public String login(Login login) {
        User user = userRepository.findByUsername(login.getUsername())
                .orElseThrow(() -> new CommonException(ErrorCode.RESOURCE_NOT_FOUND, "user"));
        if (!login.isCorrectPassword(passwordEncoder, user.getPassword())) {
            throw new CommonException(ErrorCode.WRONG_REQUEST_PARAM_DATA, "password");
        }
        return  jwtManager.createToken(user.getId(), user.getUsername(), List.of(UserRole.ROLE_MEMBER));
    }
}
