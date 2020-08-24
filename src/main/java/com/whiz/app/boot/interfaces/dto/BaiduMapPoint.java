package com.whiz.app.boot.interfaces.dto;

import com.whiz.app.boot.domain.model.MapLocation;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BaiduMapPoint {
    private String name;
    private String address;
    private String province;
    private String city;
    private String area;

    public static BaiduMapPoint from(MapLocation location) {
        return builder()
            .name(location.getName())
            .address(location.getAddress())
            .province(location.getProvince())
            .city(location.getCity())
            .area(location.getArea())
            .build();
    }

    @Override
    public String toString() {
        return "BaiduMapPoint#{" +
            "name=" + name +
            ",address=" + address +
            ",province=" + province +
            ",city=" + city +
            ",area=" + area +
            "}";
    }
}