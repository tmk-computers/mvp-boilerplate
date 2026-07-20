package com.dasp.framework.core.exception;

import com.dasp.framework.core.base.BaseException;
import org.springframework.http.HttpStatus;

public class UnauthorizedAccessException extends BaseException {

    public UnauthorizedAccessException(String message) {
        super(message, HttpStatus.UNAUTHORIZED, "UNAUTHORIZED_ACCESS");
    }
}
