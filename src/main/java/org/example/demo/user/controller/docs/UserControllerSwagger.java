package org.example.demo.user.controller.docs;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.example.demo.user.controller.dto.SignUp;
import org.springframework.http.ResponseEntity;

@Tag(name = "유저 정보")
public interface UserControllerSwagger {

    @Operation(
            summary = "회원 가입",
            description = "회원에 대한 정보를 입력받아 가입이 성공하면 저장된 회원 정보의 일부를 반환합니다."
    )
    ResponseEntity<SignUp.Response> create(SignUp.Request request);

}
