package com.whiz.app.boot.interfaces.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GenericResponseData<T> implements ResponseData {
    private ResponseDataType status;
    private String message;
    private T data;

    @Override
    public ResponseDataType getStatus() {
        return status;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public static <T> GenericResponseData<T> ok(T data) {
        return (GenericResponseData<T>) GenericResponseData.builder()
            .status(ResponseDataType.SUCCESS)
            .message("")
            .data(data)
            .build();
    }
}