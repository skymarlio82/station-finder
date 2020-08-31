package com.whiz.app.boot.application.controller;

import com.whiz.app.boot.domain.model.UserProfile;
import com.whiz.app.boot.domain.repository.UserProfileRepository;
import com.whiz.app.boot.interfaces.UserTest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/test")
public class TestController {
    private final UserProfileRepository userProfileRepository;

    public TestController(UserProfileRepository userProfileRepository) {
        this.userProfileRepository = userProfileRepository;
    }

    @GetMapping("/getAllUsers")
    public ResponseEntity<List<UserTest>> getAllUsers() {
        List<UserProfile> userProfiles = userProfileRepository.findAll();
        return ResponseEntity.ok(userProfiles.stream().map(UserTest::from).collect(Collectors.toList()));
    }

    private static final Map<String, Object> chartData = new HashMap<>();

    static {
        List<String> columns = Arrays.asList("date", "cost");
        Map<String, Object> dc = new HashMap<>();
        dc.put("date", "01/01");
        dc.put("cost", 123);
        Map<String, Object> dc1 = new HashMap<>();
        dc1.put("date", "01/02");
        dc1.put("cost", 1223);
        Map<String, Object> dc2 = new HashMap<>();
        dc2.put("date", "01/03");
        dc2.put("cost", 2123);
        Map<String, Object> dc3 = new HashMap<>();
        dc3.put("date", "01/04");
        dc3.put("cost", 4123);
        Map<String, Object> dc4 = new HashMap<>();
        dc4.put("date", "01/05");
        dc4.put("cost", 3123);
        List<Map<String, Object>> rows = Arrays.asList(dc, dc1, dc2, dc3, dc4);
        chartData.put("columns", columns);
        chartData.put("rows", rows);
    }

    @GetMapping("/chartData")
    public ResponseEntity<Map<String, Object>> getChartData() {
        return ResponseEntity.ok(chartData);
    }

    private static final Map<String, Object> chartData1 = new HashMap<>();

    static {
        List<String> columns = Arrays.asList("location", "GDP");
        Map<String, Object> lgdp = new HashMap<>();
        lgdp.put("location", "北京");
        lgdp.put("GDP", 1223);
        Map<String, Object> lgdp1 = new HashMap<>();
        lgdp1.put("location", "上海");
        lgdp1.put("GDP", 2123);
        Map<String, Object> lgdp2 = new HashMap<>();
        lgdp2.put("location", "广东");
        lgdp2.put("GDP", 4123);
        List<Map<String, Object>> rows = Arrays.asList(lgdp, lgdp1, lgdp2);
        chartData1.put("columns", columns);
        chartData1.put("rows", rows);
    }

    @GetMapping("/chartData1")
    public ResponseEntity<Map<String, Object>> getChartData1() {
        return ResponseEntity.ok(chartData1);
    }
}