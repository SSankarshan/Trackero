package com.taskero.track.exception;

public class BaseCustomException extends RuntimeException {
    private final ErrorCode errorCode;

    public BaseCustomException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }
}
