package org.example.demo.exception.controller;

import io.swagger.v3.oas.annotations.Hidden;
import lombok.extern.slf4j.Slf4j;
import org.example.demo.exception.domain.CommonException;
import org.example.demo.exception.domain.ErrorCode;
import org.example.demo.exception.controller.dto.ErrorFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Hidden // swagger 충돌 제거
@Slf4j
@RestControllerAdvice
public class GeneralExceptionAdvice {

    @ExceptionHandler(value = CommonException.class)
    public ResponseEntity<ErrorFormat> commonExceptionHandler(CommonException commonException) {
        return ResponseEntity
                .status(commonException.getErrorCode().getStatus())
                .body(new ErrorFormat(commonException.getMessage()));
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorFormat> methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException methodArgumentNotValidException) {
        String cause = methodArgumentNotValidException.getFieldError().getCode();
        String field = methodArgumentNotValidException.getFieldError().getField();

        ErrorCode errorCode = getValidExceptionErrorCode(cause);
        return ResponseEntity
                .status(errorCode.getStatus())
                .body(new ErrorFormat(errorCode.getMessage() + ": " + field));
    }

    private ErrorCode getValidExceptionErrorCode(String cause) {
        return switch (cause) {
            case "NotNull" -> ErrorCode.BAD_REQUEST_PARAM_NULL;
            case "Size" -> ErrorCode.BAD_REQUEST_PARAM_SIZE;
            case "Pattern" -> ErrorCode.BAD_REQUEST_PARAM_FORMAT;
            default -> throw new IllegalStateException("Unexpected value: " + cause);
        };
    }
}
