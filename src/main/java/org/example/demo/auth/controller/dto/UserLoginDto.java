package org.example.demo.auth.controller.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

public class UserLoginDto {

    @Data
    @Schema(name = "UserLoginRequest", description = "사용자 로그인 요청")
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
    @Schema(name = "UserLoginResponse", description = "사용자 로그인 응답")
    public static class Response {
        private String accessToken;

        public Response (){};
        public Response(String accessToken) {
            this.accessToken = accessToken;
        }
    }
}
