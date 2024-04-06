package com.example.timetracker.aspect;

import lombok.Data;

@Data
public class CompletableFutureHandler {
    private Object result;
    private Throwable exception;
}
