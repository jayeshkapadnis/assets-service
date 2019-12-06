package com.affinion.gce.exception;

public class CyberException extends Exception {
    private final ErrorCode errorCode;

    public CyberException(String message, ErrorCode errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    public CyberException(String message, Throwable cause, ErrorCode errorCode) {
        super(message, cause);
        this.errorCode = errorCode;
    }

    public CyberException(Throwable cause, ErrorCode errorCode) {
        super(cause);
        this.errorCode = errorCode;
    }

    public int getErrorCode() {
        return errorCode.code();
    }

    public int getHttpStatusCode() {
        return errorCode.statusCode();
    }
}
