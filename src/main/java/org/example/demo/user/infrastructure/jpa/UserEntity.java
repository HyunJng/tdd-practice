package org.example.demo.user.infrastructure.jpa;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.example.demo.user.domain.User;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "username", unique = true)
    private String username;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "create_at")
    private String createAt;

    public static UserEntity from(User user) {
        return UserEntity.builder()
                .id(user.getId())
                .username(user.getUsername())
                .password(user.getPassword())
                .createAt(user.getCreateAt())
                .build();
    }

    public User to() {
        return User.builder()
                .id(id)
                .username(username)
                .password(password)
                .createAt(createAt)
                .build();
    }
}
