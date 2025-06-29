package org.example.demo.auth.controller.docs;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.example.demo.auth.controller.dto.UserLoginDto;
import org.springframework.http.ResponseEntity;

@Tag(name = "회원 인증")
public interface AuthControllerSwagger {

    @Operation(
            summary = "로그인",
            description = "username 과 password 로 로그인을 합니다"
    )
    ResponseEntity<UserLoginDto.Response> login(UserLoginDto.Request request);

}
