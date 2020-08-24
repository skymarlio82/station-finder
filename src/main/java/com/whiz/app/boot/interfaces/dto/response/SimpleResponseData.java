package com.whiz.app.boot.interfaces.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SimpleResponseData implements ResponseData {
    private ResponseDataType status;
    private String message;

    @Override
    public ResponseDataType getStatus() {
        return status;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public static SimpleResponseData success() {
        return SimpleResponseData.builder()
            .status(ResponseDataType.SUCCESS)
            .message("Success")
            .build();
    }

    public static SimpleResponseData failure(ResponseDataType status, String message) {
        return SimpleResponseData.builder()
            .status(status)
            .message(message)
            .build();
    }
}