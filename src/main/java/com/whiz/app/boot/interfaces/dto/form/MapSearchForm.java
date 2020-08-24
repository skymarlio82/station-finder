package com.whiz.app.boot.interfaces.dto.form;

import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MapSearchForm {
    @ApiParam(value = "the longitude, i.e., 116.404", required = true)
    @NotNull
    @Pattern(regexp = "^(\\-|\\+)?(((\\d|[1-9]\\d|1[0-7]\\d|0{1,3})\\.\\d{0,6})|(\\d|[1-9]\\d|1[0-7]\\d|0{1,3})|180\\.0{0,6}|180)$")
    private String longitude;

    @ApiParam(value = "the latitude, i.e., 39.915", required = true)
    @NotNull
    @Pattern(regexp = "^(\\-|\\+)?([0-8]?\\d{1}\\.\\d{0,6}|90\\.0{0,6}|[0-8]?\\d{1}|90)$")
    private String latitude;

    @ApiParam(value = "the scope radius, i.e., 5000", required = true)
    @NotNull
    @Max(5000)
    @Min(50)
    private Integer radius;

    @Override
    public String toString() {
        return "MapSearchForm#{" +
            "longitude=" + longitude +
            ",latitude=" + latitude +
            '}';
    }

    public BigDecimal lng() {
        return new BigDecimal(longitude);
    }

    public BigDecimal lat() {
        return new BigDecimal(latitude);
    }
}