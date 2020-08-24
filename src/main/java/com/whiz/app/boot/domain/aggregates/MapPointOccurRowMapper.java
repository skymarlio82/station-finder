package com.whiz.app.boot.domain.aggregates;

import com.whiz.app.boot.interfaces.dto.MapPointOccurrence;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.util.StringUtils;

import java.sql.ResultSet;
import java.sql.SQLException;

public class MapPointOccurRowMapper implements RowMapper<MapPointOccurrence> {

    @Override
    public MapPointOccurrence mapRow(ResultSet rs, int rowNum) throws SQLException {
        String pointAddress = rs.getString("POINT_ADDRESS");
        Integer occurrenceCount = rs.getInt("OCCURRENCE_COUNT");
        return MapPointOccurrence.builder()
            .pointAddress(StringUtils.hasText(pointAddress) ? pointAddress : "")
            .occurrenceCount(!StringUtils.isEmpty(occurrenceCount) ? occurrenceCount : 0)
            .build();
    }
}