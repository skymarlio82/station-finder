package com.whiz.app.boot.interfaces.dto;

import io.swagger.annotations.ApiParam;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

@Data
public class PagingParamRequest {
    @ApiParam(value = "the index of current page, i.e., 0 ~ 9", required = true)
    @NotNull
    @PositiveOrZero
    private Integer currentPage;

    @ApiParam(value = "how many records in one page, i.e., 3", required = true)
    @NotNull
    @Min(1)
    private Integer pageSize;

    @Override
    public String toString() {
        return "PagingParamRequest#{" +
            "currentPage=" + currentPage +
            "pageSize=" + pageSize +
            "}";
    }
}