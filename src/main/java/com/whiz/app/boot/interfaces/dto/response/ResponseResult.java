package com.whiz.app.boot.interfaces.dto.response;

import lombok.Getter;
import lombok.ToString;


@Getter
@ToString
public class ResponseResult<T> {
    private final Integer code;
    private final String message;
    private final T data;

    private ResponseResult(ResultStatusType resultStatusType, T data) {
        this.code = resultStatusType.getCode();
        this.message = resultStatusType.getMessage();
        this.data = data;
    }

    private ResponseResult(ResultStatusType resultStatusType, String message, T data) {
        this.code = resultStatusType.getCode();
        this.message = message;
        this.data = data;
    }

    public static ResponseResult<Integer> success() {
        return new ResponseResult<>(ResultStatusType.SUCCESS, 200);
    }

    public static <T> ResponseResult<T> success(T data) {
        return new ResponseResult<>(ResultStatusType.SUCCESS, data);
    }

    public static ResponseResult<Integer> failure(ResultStatusType resultStatusType) {
        return new ResponseResult<>(resultStatusType, resultStatusType.getCode());
    }

    public static <T> ResponseResult<T> failure(ResultStatusType resultStatusType, String message, T data) {
        return new ResponseResult<>(resultStatusType, message, data);
    }
}