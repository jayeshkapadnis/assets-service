package com.affinion.gce.exception;

import com.fasterxml.jackson.annotation.JsonValue;
import org.springframework.http.HttpStatus;

import java.util.stream.Stream;

public enum ErrorCode {
    GENERAL(2, HttpStatus.INTERNAL_SERVER_ERROR),
    AUTHENTICATION(10, HttpStatus.UNAUTHORIZED),
    PERMISSION_DENIED(20, HttpStatus.FORBIDDEN),
    INVALID_ARGUMENTS(30, HttpStatus.BAD_REQUEST),
    BAD_REQUEST_PARAMS(31, HttpStatus.BAD_REQUEST),
    ITEM_NOT_FOUND(32, HttpStatus.NOT_FOUND),
    CONFLICT_ITEM(33, HttpStatus.CONFLICT);

    private int errorCode;
    private HttpStatus statusCode;

    ErrorCode(int errorCode, HttpStatus statusCode) {
        this.errorCode = errorCode;
        this.statusCode = statusCode;
    }

    @JsonValue
    public int code() {
        return errorCode;
    }

    public int statusCode() {
        return statusCode.value();
    }

    public static ErrorCode byStatusCode(HttpStatus code) {
        return Stream.of(values())
                .filter(e -> e.statusCode.equals(code))
                .findFirst()
                .orElse(ErrorCode.GENERAL);
    }
}
