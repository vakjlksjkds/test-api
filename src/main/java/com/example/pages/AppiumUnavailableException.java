package com.example.pages;

public class AppiumUnavailableException extends RuntimeException {
    public AppiumUnavailableException(String message) {
        super(message);
    }
    public AppiumUnavailableException(String message, Throwable cause) {
        super(message, cause);
    }
} 