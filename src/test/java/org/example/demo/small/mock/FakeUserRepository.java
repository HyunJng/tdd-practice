package org.example.demo.small.mock;

import org.example.demo.user.domain.User;
import org.example.demo.user.service.port.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

public class FakeUserRepository implements UserRepository {

    private AtomicLong generatedId = new AtomicLong(2);
    private List<User> data = new ArrayList<>();

    @Override
    public Optional<User> findById(long writerId) {
        return data.stream().filter(item -> item.getId().equals(writerId)).findFirst();
    }

    @Override
    public User save(User user) {
        if (user.getId() == null || user.getId() == 0L) {
            User newUser = User.builder()
                    .id(generatedId.incrementAndGet())
                    .username(user.getUsername())
                    .password(user.getPassword())
                    .createAt(user.getCreateAt())
                    .build();
            data.add(newUser);
            return newUser;
        } else {
            data.removeIf(item -> Objects.equals(item.getId(), user.getId()));
            data.add(user);
            return user;
        }
    }

    @Override
    public boolean existsByUsername(String username) {
        return data.stream().anyMatch(item -> item.getUsername().equals(username));
    }
}
