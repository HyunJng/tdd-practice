package org.example.demo.user.service;

import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.example.demo.exception.domain.CommonException;
import org.example.demo.exception.domain.ErrorCode;
import org.example.demo.common.service.port.DateHolder;
import org.example.demo.user.domain.User;
import org.example.demo.user.domain.UserCreate;
import org.example.demo.user.service.port.PasswordEncoder;
import org.example.demo.user.service.port.UserRepository;
import org.springframework.stereotype.Service;

@Builder
@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final DateHolder dateHolder;
    private final PasswordEncoder passwordEncoder;

    public User save(UserCreate userCreate) {
        if (userRepository.existsByUsername(userCreate.getUsername())) {
            throw new CommonException(ErrorCode.ALREADY_EXISTS_USER);
        }
        userCreate.changePassword(passwordEncoder);
        return userRepository.save(User.from(userCreate, dateHolder));
    }

    public User getById(long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new CommonException(ErrorCode.RESOURCE_NOT_FOUND, "USER"));
    }


}
