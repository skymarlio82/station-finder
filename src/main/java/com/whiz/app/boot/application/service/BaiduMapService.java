package com.whiz.app.boot.application.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.whiz.app.boot.application.RemoteDataParsedException;
import com.whiz.app.boot.domain.aggregates.MapPointOccurRowMapper;
import com.whiz.app.boot.domain.model.MapLocation;
import com.whiz.app.boot.interfaces.dto.MapPointOccurrence;
import com.whiz.app.boot.domain.repository.MapLocationRepository;
import com.whiz.app.boot.infrastructure.config.BaiduMapProperties;
import com.whiz.app.boot.interfaces.dto.BaiduMapResult;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class BaiduMapService {
    private final BaiduMapProperties baiduMapProperties;
    private final MapLocationRepository mapLocationRepository;
    private final ObjectMapper objectMapper;
    private final NamedParameterJdbcTemplate jdbcTemplate;

    public BaiduMapService(
        BaiduMapProperties baiduMapProperties,
        MapLocationRepository mapLocationRepository,
        ObjectMapper objectMapper,
        NamedParameterJdbcTemplate jdbcTemplate) {
        this.baiduMapProperties = baiduMapProperties;
        this.mapLocationRepository = mapLocationRepository;
        this.objectMapper = objectMapper;
        this.jdbcTemplate = jdbcTemplate;
    }

    @Transactional(readOnly = true)
    public List<MapPointOccurrence> getTop3() {
        String sql = "select s1.NAME as POINT_ADDRESS, s1.NAME_CNT as OCCURRENCE_COUNT " +
            "from ( " +
            "select " +
            "count(concat(ml1.NAME, ' ', ml1.ADDRESS)) as NAME_CNT, concat(ml1.NAME, ' ', ml1.ADDRESS) as NAME " +
            "from MAP_LOCATION ml1 where ml1.NAME like '%加油站%' group by concat(ml1.NAME, ' ', ml1.ADDRESS) " +
            "order by count(concat(ml1.NAME, ' ', ml1.ADDRESS)) desc limit 3 " +
            ") s1 ";
        return jdbcTemplate.query(sql, new MapSqlParameterSource(), new MapPointOccurRowMapper());
    }

    @SneakyThrows
    @Transactional
    public List<MapLocation> searchOnPurpose(
        BigDecimal lat, BigDecimal lng, Integer radius, String query,String username) {
        RestTemplate restTemplate = new RestTemplate();
        String mapSearchUrl = baiduMapProperties.getMapUrl()
            + "?query=" + query
            + "&location=" + lat + "," + lng
            + "&radius=" + radius
            + "&output=json"
            + "&ak=" + baiduMapProperties.getMapAk();
        ResponseEntity<String> searchedResult = restTemplate.getForEntity(mapSearchUrl, String.class);
        String searchedBody = searchedResult.getBody();
        log.debug("searchedBody = {}", searchedBody);
        BaiduMapResult mapResult = null;
        try {
            mapResult = objectMapper.readValue(searchedBody, BaiduMapResult.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            throw new RemoteDataParsedException(e.getMessage());
        }
        assert mapResult != null;
        List<MapLocation> mls = mapResult.getResults().stream()
            .map(p -> new MapLocation(0L,p.getName(), p.getAddress(), p.getProvince(), p.getCity(), p.getArea(),
                "street-id", username, LocalDate.now())).collect(Collectors.toList());
        return mapLocationRepository.saveAll(mls);
    }
}