package com.app.adminservice.exception;

import lombok.extern.slf4j.Slf4j;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<Map<String, Object>> handleResponseStatus(
            ResponseStatusException ex, HttpServletRequest request) {
        return ResponseEntity.status(ex.getStatusCode()).body(
                buildError(ex.getStatusCode().value(), ex.getReason(),
                        ex.getReason(), request.getRequestURI()));
    }

    @ExceptionHandler(feign.FeignException.class)
    public ResponseEntity<Map<String, Object>> handleFeignException(
            feign.FeignException ex, HttpServletRequest request) {
        log.error("Feign error: {}", ex.getMessage());

        int status = ex.status() > 0 ? ex.status() : 503;
        String message = "Service communication error: " + ex.getMessage();

        return ResponseEntity.status(status).body(
                buildError(status, "Service Error", message, request.getRequestURI()));
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
