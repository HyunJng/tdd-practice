package org.example.demo.exception.domain;

import lombok.Getter;
import org.springframework.http.HttpStatus;

public enum ErrorCode {
    RESOURCE_NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지않는 %1 입니다"),
    BAD_REQUEST_PARAM_FORMAT(HttpStatus.BAD_REQUEST, "지원하지 않는 형식"),
    BAD_REQUEST_PARAM_SIZE(HttpStatus.BAD_REQUEST, "파라미터 길이 오류"),
    BAD_REQUEST_PARAM_NULL(HttpStatus.BAD_REQUEST, "필수 파라미터 누락"),
    UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "권한이 없습니다"),
    ALREADY_EXISTS_USER(HttpStatus.NOT_ACCEPTABLE, "이미 존재하는 회원입니다");

    @Getter
    private final HttpStatus status;
    private final String message;

    ErrorCode(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }

    public String getMessage(String... args) {
        String resultMessage = message;
        if (args == null || args.length == 0 || !message.contains("%")) {
            return resultMessage;
        }
        for (int i = 0; i < args.length; i++) {
            resultMessage = resultMessage.replaceAll("%" + (i + 1), args[i]);
        }
        return resultMessage;
    }
}
