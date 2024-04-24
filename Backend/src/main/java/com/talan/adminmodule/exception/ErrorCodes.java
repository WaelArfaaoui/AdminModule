package com.talan.AdminModule.exception;
public enum ErrorCodes {

    RULE_NOT_FOUND(1000),
    RULE_NOT_VALID(1001),
    RULE_ALREADY_IN_USE(1002),

    ATTRIBUTE_NOT_FOUND(2000),
    ATTRIBUTE_NOT_VALID(2001),
    ATTRIBUTE_ALREADY_IN_USE(2002),

    INVALID_ENTITY(3000) ;

    private final int code;

    ErrorCodes(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
