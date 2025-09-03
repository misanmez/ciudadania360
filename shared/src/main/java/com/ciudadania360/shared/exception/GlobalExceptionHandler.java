package com.ciudadania360.shared.exception;

import jakarta.validation.ValidationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.time.OffsetDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<ErrorResponse> handleValidation(ValidationException ex, WebRequest request) {
        log.warn("Validation error: {}", ex.getMessage());
        return buildResponse("VALIDATION_ERROR", ex.getMessage(), HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ErrorResponse> handleBadRequest(BadRequestException ex, WebRequest request) {
        log.warn("BadRequest: {}", ex.getMessage());
        return buildResponse("BAD_REQUEST", ex.getMessage(), HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ErrorResponse> handleBusiness(BusinessException ex, WebRequest request) {
        log.warn("Business exception: {}", ex.getMessage());
        return buildResponse("BUSINESS_ERROR", ex.getMessage(), HttpStatus.CONFLICT, request);
    }

    @ExceptionHandler(SecurityException.class)
    public ResponseEntity<ErrorResponse> handleSecurity(SecurityException ex, WebRequest request) {
        log.warn("Security exception: {}", ex.getMessage());
        return buildResponse("SECURITY_ERROR", ex.getMessage(), HttpStatus.FORBIDDEN, request);
    }

    @ExceptionHandler(SystemException.class)
    public ResponseEntity<ErrorResponse> handleSystem(SystemException ex, WebRequest request) {
        log.error("System exception", ex);
        return buildResponse("SYSTEM_ERROR", ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR, request);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGeneric(Exception ex, WebRequest request) {
        log.error("Unexpected error", ex);
        return buildResponse("INTERNAL_ERROR", "Unexpected server error", HttpStatus.INTERNAL_SERVER_ERROR, request);
    }

    private ResponseEntity<ErrorResponse> buildResponse(String code, String message, HttpStatus status, WebRequest request) {
        Map<String, Object> details = new HashMap<>();
        details.put("path", request.getDescription(false).replace("uri=", ""));

        ErrorResponse body = new ErrorResponse(
                UUID.randomUUID().toString(),
                code,
                message,
                OffsetDateTime.now(),
                details
        );

        return ResponseEntity.status(status)
                .header("X-Request-Id", body.transactionId())
                .body(body);
    }
}
