package com.dasp.framework.core.exception;

import com.dasp.framework.core.base.BaseException;
import org.springframework.http.HttpStatus;

public class BusinessRuleViolationException extends BaseException {

    public BusinessRuleViolationException(String message) {
        super(message, HttpStatus.BAD_REQUEST, "BUSINESS_RULE_VIOLATION");
    }
}
