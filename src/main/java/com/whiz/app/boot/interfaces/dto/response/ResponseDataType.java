package com.whiz.app.boot.interfaces.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum ResponseDataType {
    SUCCESS("200", "SUCCESS"),
    BAD_REQUEST("400", "BAD_REQUEST"),
    INTERNAL_ERROR("500", "BAD_REQUEST")
    ;

    ResponseDataType(String statusCode, String statusDescription) {
        this.statusCode = statusCode;
        this.statusDescription = statusDescription;
    }

    private String statusCode;
    private String statusDescription;

    public String getStatusCode() {
        return statusCode;
    }

    public String getStatusDescription() {
        return statusDescription;
    }
}