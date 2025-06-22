package org.example.demo.user.infrastructure.jpa;

import org.example.demo.user.infrastructure.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserJpaRepository extends JpaRepository<UserEntity, Long> {

    Optional<UserEntity> findById(long userId);

    boolean existsByUsername(String userName);

    Optional<UserEntity> findByUsername(String username);
}
