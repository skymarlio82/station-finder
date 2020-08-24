
package com.whiz.app.boot.interfaces.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BaiduLocation {
    private BigDecimal lat;
    private BigDecimal lng;

}