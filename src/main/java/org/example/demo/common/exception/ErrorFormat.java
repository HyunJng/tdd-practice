package org.example.demo.common.exception;

import lombok.Getter;

@Getter
public class ErrorFormat {
    private final String message;

    public ErrorFormat(String message) {
        this.message = message;
    }
}
