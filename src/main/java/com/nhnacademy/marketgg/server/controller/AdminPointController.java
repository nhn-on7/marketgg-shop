package com.nhnacademy.marketgg.server.controller;

import com.nhnacademy.marketgg.server.dto.response.PointRetrieveResponse;
import com.nhnacademy.marketgg.server.service.PointService;
import java.net.URI;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/shop/v1/admin/points")
@RequiredArgsConstructor
public class AdminPointController {

    private final PointService pointService;

    private static final String DEFAULT_ADMIN = "/shop/v1/admin";

    @GetMapping
    public ResponseEntity<List<PointRetrieveResponse>> adminRetrievePointHistory() {
        List<PointRetrieveResponse> responses = pointService.adminRetrievePointHistories();

        return ResponseEntity.status(HttpStatus.OK)
                .location(URI.create(DEFAULT_ADMIN + "/points"))
                .body(responses);
    }

}
