package org.example.demo.user.controller.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;
import org.example.demo.user.domain.User;

public class SignUp {

    @Data
    public static class Request {

        @NotNull(message = "BAD_REQUEST_PARAM_NULL")
        @Size(min = 4, max = 10, message = "BAD_REQUEST_PARAM_SIZE")
        @Pattern(regexp = "^[a-z0-9]+$", message = "BAD_REQUEST_PARAM_FORMAT")
        private String username;

        @NotNull(message = "BAD_REQUEST_PARAM_NULL")
        @Size(min = 8, max = 15, message = "BAD_REQUEST_PARAM_SIZE")
        @Pattern(regexp = "^[a-zA-Z0-9]+$", message = "BAD_REQUEST_PARAM_FORMAT")
        private String password;

    }

    @Data
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
