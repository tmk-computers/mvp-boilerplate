package com.dasp.framework.core.exception;

import com.dasp.framework.core.base.BaseException;
import com.dasp.framework.core.base.BaseResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BaseException.class)
    public ResponseEntity<BaseResponse<Void>> handleBaseException(BaseException ex) {
        log.error("Custom Application Exception: [{}] {}", ex.getErrorCode(), ex.getMessage());
        return ResponseEntity.status(ex.getStatus())
                .body(BaseResponse.error(ex.getMessage(), ex.getErrorCode()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<BaseResponse<Void>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        List<BaseResponse.ValidationError> validationErrors = ex.getBindingResult().getAllErrors().stream()
                .map(error -> {
                    String fieldName = ((FieldError) error).getField();
                    Object rejectedValue = ((FieldError) error).getRejectedValue();
                    String errorMessage = error.getDefaultMessage();
                    return new BaseResponse.ValidationError(fieldName, rejectedValue, errorMessage);
                })
                .collect(Collectors.toList());

        log.warn("Validation error encountered: {} fields failed validation", validationErrors.size());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(BaseResponse.error("Validation failed", "VALIDATION_ERROR", validationErrors));
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<BaseResponse<Void>> handleAuthenticationException(AuthenticationException ex) {
        log.error("Authentication Failure: {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(BaseResponse.error(ex.getMessage(), "AUTHENTICATION_FAILURE"));
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<BaseResponse<Void>> handleAccessDeniedException(AccessDeniedException ex) {
        log.error("Access Denied: {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body(BaseResponse.error("Access Denied: Insufficient privileges", "ACCESS_DENIED"));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<BaseResponse<Void>> handleGlobalException(Exception ex) {
        log.error("Unhandled Internal Server Error", ex);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(BaseResponse.error("An unexpected internal server error occurred", "INTERNAL_SERVER_ERROR"));
    }
}
