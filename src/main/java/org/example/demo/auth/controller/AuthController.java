package org.example.demo.auth.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.demo.auth.controller.docs.AuthControllerSwagger;
import org.example.demo.auth.controller.dto.UserLoginDto;
import org.example.demo.auth.domain.Login;
import org.example.demo.auth.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/api/auth")
@RestController
public class AuthController implements AuthControllerSwagger {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<UserLoginDto.Response> login(@Valid @RequestBody UserLoginDto.Request request) {
        String token = authService.login(Login.from(request));
        return ResponseEntity.ok(new UserLoginDto.Response(token));
    }
}
