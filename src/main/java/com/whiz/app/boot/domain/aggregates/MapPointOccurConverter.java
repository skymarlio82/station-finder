package com.whiz.app.boot.domain.aggregates;

import com.whiz.app.boot.interfaces.dto.MapPointOccurrence;

import java.util.List;

public class MapPointOccurConverter {

    public static MapPointOccurrence from(Object obj) {
        List<Object> objs = (List<Object>)obj;
        return MapPointOccurrence.builder()
            .pointAddress(String.valueOf(objs.get(0)))
            .occurrenceCount(1)
            .build();
    }
}