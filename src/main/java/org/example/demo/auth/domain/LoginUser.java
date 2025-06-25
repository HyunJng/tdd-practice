package org.example.demo.auth.domain;

import lombok.Builder;
import lombok.Getter;
import org.example.demo.auth.infrastructure.security.CustomUserDetails;
import org.example.demo.user.domain.UserRole;

import java.util.List;

@Getter
public class LoginUser {

    private Long id;
    private String username;
    private List<UserRole> roles;

    @Builder
    public LoginUser(long id, String username, List<UserRole> roles) {
        this.id = id;
        this.username = username;
        this.roles = roles;
    }


    public static LoginUser from(CustomUserDetails userDetails) {
        return LoginUser.builder()
                .id(userDetails.getId())
                .username(userDetails.getUsername())
                .roles(userDetails.getAuthorities().stream().map(authority -> UserRole.valueOf(authority.getAuthority())).toList())
                .build();
    }

}
