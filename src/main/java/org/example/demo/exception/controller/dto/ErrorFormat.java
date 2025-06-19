package org.example.demo.exception.controller.dto;

import lombok.Getter;

@Getter
public class ErrorFormat {
    private final String message;

    public ErrorFormat(String message) {
        this.message = message;
    }
}
