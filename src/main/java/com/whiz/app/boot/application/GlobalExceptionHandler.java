package com.whiz.app.boot.application;

import com.whiz.app.boot.interfaces.dto.response.ResponseDataType;
import com.whiz.app.boot.interfaces.dto.response.SimpleResponseData;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.List;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BindException.class)
    public ResponseEntity<SimpleResponseData> handleValidationException(BindException be) {
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
        return ResponseEntity.ok(SimpleResponseData.failure(ResponseDataType.BAD_REQUEST, errorMessage.toString()));
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<SimpleResponseData> handleInternalError(IllegalArgumentException iae) {
        return ResponseEntity.ok(SimpleResponseData.failure(ResponseDataType.INTERNAL_ERROR, iae.getMessage()));
    }

    @ExceptionHandler({
        RemoteDataParsedException.class
    })
    public ResponseEntity<SimpleResponseData> handleServiceLayerError(CommonBizException e) {
        return ResponseEntity.ok(SimpleResponseData.failure(ResponseDataType.INTERNAL_ERROR, e.getMessage()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<SimpleResponseData> handleGeneralError(Exception e) {
        return ResponseEntity.ok(SimpleResponseData.failure(ResponseDataType.INTERNAL_ERROR, e.getMessage()));
    }
}