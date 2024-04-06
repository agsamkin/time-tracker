package com.example.timetracker.exception;

import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@RestControllerAdvice
public class ErrorHandlingControllerAdvice {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorMessage> handleInvalidArgument(ConstraintViolationException ex, WebRequest webRequest) {
        return generateErrorMessage(HttpStatus.BAD_REQUEST, ex, webRequest);
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorMessage> handleInternalError(Exception ex, WebRequest webRequest) {
        return generateErrorMessage(HttpStatus.INTERNAL_SERVER_ERROR, ex, webRequest);
    }

    private ResponseEntity<ErrorMessage> generateErrorMessage(HttpStatus status, Throwable ex, WebRequest request){
        ErrorMessage errorMessage = new ErrorMessage.ErrorMessageBuilder()
                .statusCode(status.value())
                .timeStamp(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(LocalDateTime.now()))
                .message(ex.getMessage())
                .description(request.getDescription(false))
                .build();
        return new ResponseEntity<>(errorMessage, status);
    }

}