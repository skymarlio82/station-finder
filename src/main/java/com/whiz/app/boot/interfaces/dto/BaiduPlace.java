
package com.whiz.app.boot.interfaces.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BaiduPlace {
    private String name;
    private BaiduLocation location;
    private String address;
    private String province;
    private String city;
    private String area;
    private String telephone;
    private Integer detail;
    private String uid;

}