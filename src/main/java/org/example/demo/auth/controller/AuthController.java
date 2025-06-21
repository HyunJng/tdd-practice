package org.example.demo.auth.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.demo.auth.controller.dto.UserLoginDto;
import org.example.demo.auth.domain.UserLogin;
import org.example.demo.auth.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Tag(name = "회원 인증")
@RequiredArgsConstructor
@RequestMapping("/api/auth")
@Controller
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<UserLoginDto.Response> login(@Valid @RequestBody UserLoginDto.Request request) {
        String token = authService.login(UserLogin.from(request));
        return ResponseEntity.ok(new UserLoginDto.Response(token));
    }
}
