package com.nhnacademy.marketgg.server.controller;

import com.nhnacademy.marketgg.server.cloud.StorageService;
import com.nhnacademy.marketgg.server.dto.response.common.CommonResponse;
import com.nhnacademy.marketgg.server.dto.response.common.SingleResponse;
import java.io.InputStream;
import java.net.URI;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/storage")
@RequiredArgsConstructor
public class StorageController {

    private final StorageService storageService;

    @GetMapping("/{assetNo}")
    public ResponseEntity<CommonResponse> retrieveReviewDetails(
        @PathVariable(name = "assetNo") final Long assetId) {

        SingleResponse<InputStream> response = storageService.downloadObject();

        return ResponseEntity.status(HttpStatus.OK)
                             .location(URI.create(DEFAULT_REVIEW_URI + productId + REVIEW_PATH + reviewId))
                             .contentType(MediaType.APPLICATION_JSON).body(response);
    }
}
