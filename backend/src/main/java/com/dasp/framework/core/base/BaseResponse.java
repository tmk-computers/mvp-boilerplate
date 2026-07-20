package com.dasp.framework.core.base;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.slf4j.MDC;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BaseResponse<T> {

    private boolean success;
    private String message;
    private T data;
    private String errorCode;
    private List<ValidationError> errors;
    @Builder.Default
    private LocalDateTime timestamp = LocalDateTime.now();
    private String traceId;

    public static <T> BaseResponse<T> success(T data) {
        return BaseResponse.<T>builder()
                .success(true)
                .message("Operation completed successfully")
                .data(data)
                .traceId(MDC.get("traceId"))
                .build();
    }

    public static <T> BaseResponse<T> success(T data, String message) {
        return BaseResponse.<T>builder()
                .success(true)
                .message(message)
                .data(data)
                .traceId(MDC.get("traceId"))
                .build();
    }

    public static <T> BaseResponse<T> error(String message, String errorCode) {
        return BaseResponse.<T>builder()
                .success(false)
                .message(message)
                .errorCode(errorCode)
                .traceId(MDC.get("traceId"))
                .build();
    }

    public static <T> BaseResponse<T> error(String message, String errorCode, List<ValidationError> errors) {
        return BaseResponse.<T>builder()
                .success(false)
                .message(message)
                .errorCode(errorCode)
                .errors(errors)
                .traceId(MDC.get("traceId"))
                .build();
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ValidationError {
        private String field;
        private Object rejectedValue;
        private String message;
    }
}
