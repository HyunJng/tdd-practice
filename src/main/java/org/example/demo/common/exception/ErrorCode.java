package org.example.demo.common.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

public enum ErrorCode {
    RESOURCE_NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지않는 %1 입니다"),
    BAD_REQUEST_PARAM_FORMAT(HttpStatus.BAD_REQUEST, "지원하지 않는 형식의 요청입니다");

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
