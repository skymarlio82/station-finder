package com.whiz.app.boot.interfaces.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MapPointOccurrence {
    private String pointAddress;
    private Integer occurrenceCount;

    @Override
    public String toString() {
        return "MapPointOccurrence#{" +
            "pointAddress=" + pointAddress +
            ",occurrenceCount=" + occurrenceCount +
            "}";
    }
}