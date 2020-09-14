package com.whiz.app.boot.application;

import com.whiz.app.boot.interfaces.dto.response.ResponseResult;
import com.whiz.app.boot.interfaces.dto.response.ResultStatusType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.List;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BindException.class)
    public ResponseEntity<ResponseResult<String>> handleValidationException(BindException be) {
        StringBuilder errorMessage = new StringBuilder();
        List<FieldError> fieldErrorList = be.getFieldErrors();
        for (FieldError fieldError : fieldErrorList) {
            errorMessage.append(fieldError.getObjectName())
                .append(".")
                .append(fieldError.getField())
                .append(" : ")
                .append(fieldError.getDefaultMessage())
                .append(";");
        }
        return ResponseEntity.ok(
            ResponseResult.failure(ResultStatusType.BAD_REQUEST, errorMessage.toString(), errorMessage.toString()));
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ResponseResult<String>> handleInternalError(IllegalArgumentException iae) {
        return ResponseEntity.ok(
            ResponseResult.failure(ResultStatusType.INTERNAL_SERVER_ERROR, iae.getMessage(), iae.getMessage()));
    }

    @ExceptionHandler({
        RemoteDataParsedException.class
    })
    public ResponseEntity<ResponseResult<String>> handleServiceLayerError(CommonBizException cbe) {
        return ResponseEntity.ok(
            ResponseResult.failure(ResultStatusType.INTERNAL_SERVER_ERROR, cbe.getMessage(), cbe.getMessage()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ResponseResult<String>> handleGeneralError(Exception e) {
        return ResponseEntity.ok(
            ResponseResult.failure(ResultStatusType.INTERNAL_SERVER_ERROR, e.getMessage(), e.getMessage()));
    }
}