package org.example.demo.user.controller.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;
import org.example.demo.user.domain.User;

public class SignUp {

    @Data
    @Schema(name = "SignUpRequest", description = "회원 가입 요청")
    public static class Request {

        @NotNull
        @Size(min = 4, max = 10)
        @Pattern(regexp = "^[a-z0-9]+$")
        private String username;

        @NotNull
        @Size(min = 8, max = 15)
        @Pattern(regexp = "^[a-zA-Z0-9]+$")
        private String password;

    }

    @Data
    @Schema(name = "SignUpResponse", description = "회원 가입 요청")
    public static class Response {
        private long id;
        private String username;
        private String createAt;

        @Builder
        public Response(long id, String username, String createAt) {
            this.id = id;
            this.username = username;
            this.createAt = createAt;
        }

        public static Response from(User user) {
            return Response.builder()
                    .id(user.getId())
                    .username(user.getUsername())
                    .createAt(user.getCreateAt())
                    .build();
        }
    }
}
