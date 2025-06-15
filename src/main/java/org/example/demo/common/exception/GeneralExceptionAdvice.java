package org.example.demo.common.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GeneralExceptionAdvice {

    @ExceptionHandler(value = CommonException.class)
    public ResponseEntity<ErrorFormat> commonExceptionHandler(CommonException commonException) {
        return ResponseEntity
                .status(commonException.getErrorCode().getStatus())
                .body(new ErrorFormat(commonException.getMessage()));
    }
}
