package org.example.demo.common.exception;

import io.swagger.v3.oas.annotations.Hidden;
import lombok.extern.slf4j.Slf4j;
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
        ErrorCode errorCode = ErrorCode.valueOf(methodArgumentNotValidException.getFieldError().getDefaultMessage());
        return ResponseEntity
                .status(errorCode.getStatus())
                .body(new ErrorFormat(errorCode.getMessage()));
    }
}
