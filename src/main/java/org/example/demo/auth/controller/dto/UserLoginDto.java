package org.example.demo.auth.controller.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

public class UserLoginDto {

    @Data
    public static class Request {
        @NotBlank
        private String username;
        @NotBlank
        private String password;

        @Builder
        public Request(String username, String password) {
            this.username = username;
            this.password = password;
        }
    }

    @Data
    public static class Response {
        private String accessToken;

        public Response (){};
        public Response(String accessToken) {
            this.accessToken = accessToken;
        }
    }
}
