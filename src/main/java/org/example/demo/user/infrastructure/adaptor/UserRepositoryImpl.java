package org.example.demo.user.infrastructure.adaptor;

import lombok.RequiredArgsConstructor;
import org.example.demo.user.domain.User;
import org.example.demo.user.infrastructure.UserEntity;
import org.example.demo.user.infrastructure.UserJpaRepository;
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

    @Override
    public boolean existsByUsername(String username) {
        return userJpaRepository.existsByUsername(username);
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return userJpaRepository.findByUsername(username).map(UserEntity::to);
    }
}
