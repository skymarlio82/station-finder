package com.whiz.app.boot.interfaces.dto.response;

import lombok.Getter;
import lombok.ToString;
import org.springframework.http.HttpStatus;

@ToString
@Getter
public enum ResultStatusType {
    SUCCESS(0, "OK"),
    BAD_REQUEST(HttpStatus.BAD_REQUEST.value(), "Bad Request"),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Internal Server Error"),
    ;

    private final Integer code;
    private final String message;

    ResultStatusType(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}