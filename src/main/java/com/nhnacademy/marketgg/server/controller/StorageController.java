package com.nhnacademy.marketgg.server.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.nhnacademy.marketgg.server.cloud.StorageService;
import com.nhnacademy.marketgg.server.dto.response.ImageResponse;
import com.nhnacademy.marketgg.server.dto.response.common.CommonResponse;
import com.nhnacademy.marketgg.server.dto.response.common.SingleResponse;
import java.io.InputStream;
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
        @PathVariable(name = "assetNo") final Long assetId) throws JsonProcessingException {

        ImageResponse imageResponse = storageService.retrieveImage(assetId);
        InputStream inputStream = storageService.downloadObject("on7_storage", imageResponse.getName());

        return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON)
                             .body(new SingleResponse<>(inputStream));
    }
}
