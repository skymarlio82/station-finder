package com.whiz.app.boot.domain.repository;

import com.whiz.app.boot.domain.model.MapLocation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MapLocationRepository extends JpaRepository<MapLocation, Long>, JpaSpecificationExecutor<MapLocation> {

    @Query(nativeQuery = true, value = "select concat(s1.NAME, ' ', '[', s1.NAME_CNT, ']') as POINT_ADDRESS " +
        "from ( " +
        "select count(concat(ml1.NAME, ' ', ml1.ADDRESS)) as NAME_CNT, concat(ml1.NAME, ' ', ml1.ADDRESS) as NAME from MAP_LOCATION ml1 where ml1.NAME like '%加油站%' group by concat(ml1.NAME, ' ', ml1.ADDRESS) order by count(concat(ml1.NAME, ' ', ml1.ADDRESS)) desc limit 3 " +
        ") s1 ")
    List<String> findTop3();
}