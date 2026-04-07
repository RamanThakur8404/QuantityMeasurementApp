package com.app.userservice.exception;

import lombok.extern.slf4j.Slf4j;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidation(
            MethodArgumentNotValidException ex, HttpServletRequest request) {

        String message = ex.getBindingResult().getFieldErrors().stream()
                .map(FieldError::getDefaultMessage)
                .collect(Collectors.joining("; "));

        return ResponseEntity.badRequest().body(
                buildError(HttpStatus.BAD_REQUEST.value(), "Validation Error", message, request.getRequestURI()));
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Map<String, Object>> handleConstraint(
            ConstraintViolationException ex, HttpServletRequest request) {

        String message = ex.getConstraintViolations().stream()
                .map(v -> v.getPropertyPath() + ": " + v.getMessage())
                .collect(Collectors.joining("; "));

        return ResponseEntity.badRequest().body(
                buildError(HttpStatus.BAD_REQUEST.value(), "Validation Error", message, request.getRequestURI()));
    }

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<Map<String, Object>> handleResponseStatus(
            ResponseStatusException ex, HttpServletRequest request) {

        return ResponseEntity.status(ex.getStatusCode()).body(
                buildError(ex.getStatusCode().value(), ex.getReason(), ex.getReason(), request.getRequestURI()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleGlobal(
            Exception ex, HttpServletRequest request) {

        log.error("Unhandled error: {}", ex.getMessage());
        return ResponseEntity.internalServerError().body(
                buildError(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                        "Internal Server Error", ex.getMessage(), request.getRequestURI()));
    }

    private Map<String, Object> buildError(int status, String error, String message, String path) {
        Map<String, Object> body = new HashMap<>();
        body.put("timestamp", LocalDateTime.now().toString());
        body.put("status", status);
        body.put("error", error);
        body.put("message", message);
        body.put("path", path);
        return body;
    }
}
