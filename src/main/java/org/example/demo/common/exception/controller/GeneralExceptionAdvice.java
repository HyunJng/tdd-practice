package org.example.demo.common.exception.controller;

import io.swagger.v3.oas.annotations.Hidden;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.example.demo.common.exception.controller.dto.ErrorFormat;
import org.example.demo.common.exception.domain.CommonException;
import org.example.demo.common.exception.domain.ErrorCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Hidden // swagger 충돌 제거
@Slf4j
@RestControllerAdvice
public class GeneralExceptionAdvice {

    @ExceptionHandler(value = CommonException.class)
    public ResponseEntity<ErrorFormat> commonExceptionHandler(CommonException commonException) {
        log.debug("[CommonException] message = {}", commonException.getMessage());

        return ResponseEntity
                .status(commonException.getErrorCode().getStatus())
                .body(new ErrorFormat(commonException.getMessage()));
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorFormat> methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException methodArgumentNotValidException,
                                                                              HttpServletRequest request) {
        String requestURI = request.getRequestURI();
        String cause = methodArgumentNotValidException.getFieldError().getCode();
        String field = methodArgumentNotValidException.getFieldError().getField();
        Object content = methodArgumentNotValidException.getBindingResult().getRawFieldValue(field);
        log.debug("[BindingException] error = {}:{}, uri={}, request={}", cause, field, requestURI, content); // TODO: masking 처리

        ErrorCode errorCode = getValidExceptionErrorCode(cause);
        return ResponseEntity
                .status(errorCode.getStatus())
                .body(new ErrorFormat(errorCode.getMessage() + ": " + field));
    }

    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<ErrorFormat> exceptionHandler(Exception exception) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        String requestURI = request.getRequestURI();
        String method = request.getMethod();

        log.error("[Unknown Error] uri={}, request={}", method, requestURI, exception);
        return ResponseEntity
                .internalServerError()
                .body(new ErrorFormat("미지정 오류. 개발자 문의 필요"));
    }

    private ErrorCode getValidExceptionErrorCode(String cause) {
        return switch (cause) {
            case "NotNull", "NotBlank" -> ErrorCode.BAD_REQUEST_PARAM_NULL;
            case "Size" -> ErrorCode.BAD_REQUEST_PARAM_SIZE;
            case "Pattern" -> ErrorCode.BAD_REQUEST_PARAM_FORMAT;
            default -> throw new IllegalStateException("Unexpected value: " + cause);
        };
    }
}
