
package com.whiz.app.boot.interfaces.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BaiduMapResult {
    private Integer status;
    private String message;
    private List<BaiduPlace> results = new ArrayList<BaiduPlace>();

}