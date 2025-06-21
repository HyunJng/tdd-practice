package org.example.demo.user.service.port;

import org.example.demo.user.domain.User;

import java.util.Optional;

public interface UserRepository {
    Optional<User> findById(long writerId);

    User save(User user);

    boolean existsByUsername(String username);

    Optional<User> findByUsername(String username);
}
