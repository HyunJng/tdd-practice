package org.example.demo.user.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.demo.user.controller.docs.UserControllerSwagger;
import org.example.demo.user.controller.dto.SignUp;
import org.example.demo.user.domain.UserCreate;
import org.example.demo.user.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/users")
public class UserController implements UserControllerSwagger {

    private final UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<SignUp.Response> create(@Valid @RequestBody SignUp.Request request) {
        SignUp.Response response = SignUp.Response.from(userService.save(UserCreate.from(request)));
        return ResponseEntity.ok(response);
    }

}
