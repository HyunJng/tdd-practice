package org.example.demo.user.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.demo.user.controller.dto.SignUp;
import org.example.demo.user.domain.UserCreate;
import org.example.demo.user.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "유저정보 Controller")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<SignUp.Response> create(@Valid @RequestBody SignUp.Request request) {
        SignUp.Response response = SignUp.Response.from(userService.save(UserCreate.from(request)));
        return ResponseEntity.ok(response);
    }

}
