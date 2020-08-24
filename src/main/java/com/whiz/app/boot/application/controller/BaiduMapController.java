package com.whiz.app.boot.application.controller;

import com.whiz.app.boot.application.service.BaiduMapService;
import com.whiz.app.boot.domain.model.MapLocation;
import com.whiz.app.boot.interfaces.dto.BaiduMapPoint;
import com.whiz.app.boot.interfaces.dto.MapPointOccurrence;
import com.whiz.app.boot.interfaces.dto.form.MapSearchForm;
import com.whiz.app.boot.interfaces.dto.response.GenericResponseData;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@Api(tags = "RESTful APIs of User Provided Outside")
@Slf4j
@RestController
@RequestMapping("/api/map/baidu")
public class BaiduMapController {

    private final BaiduMapService baiduMapService;

    public BaiduMapController(BaiduMapService baiduMapService) {
        this.baiduMapService = baiduMapService;
    }

    @ApiOperation(value = "Search on Baidu Map for 中石化$中石油", nickname = "FindStation")
    @GetMapping("/search")
    @ResponseBody
    public ResponseEntity<GenericResponseData<List<BaiduMapPoint>>> searchOnMap(
        @Valid MapSearchForm mapSearchForm) {
        UserDetails principal = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<MapLocation> mls = baiduMapService.searchOnPurpose(mapSearchForm.lat(), mapSearchForm.lng(),
            mapSearchForm.getRadius(), "中石化$中石油", principal.getUsername());
        return ResponseEntity.ok(
            GenericResponseData.ok(mls.stream().map(BaiduMapPoint::from).collect(Collectors.toList())));
    }

    @ApiOperation(value = "Analysis most popular site (中石化$中石化)", nickname = "FindTop3")
    @GetMapping("/top3")
    @ResponseBody
    public ResponseEntity<GenericResponseData<List<MapPointOccurrence>>> fetchTop3() {
        List<MapPointOccurrence> list = baiduMapService.getTop3();
        return ResponseEntity.ok(GenericResponseData.ok(list));
    }
}