package org.example.demo.user.infrastructure;

import lombok.RequiredArgsConstructor;
import org.example.demo.user.domain.User;
import org.example.demo.user.service.port.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserRepositoryImpl implements UserRepository {

    private final UserJpaRepository userJpaRepository;

    @Override
    public Optional<User> findById(long userId) {
        return userJpaRepository.findById(userId).map(UserEntity::to);
    }

    @Override
    public User save(User user) {
        return userJpaRepository.save(UserEntity.from(user)).to();
    }
}
