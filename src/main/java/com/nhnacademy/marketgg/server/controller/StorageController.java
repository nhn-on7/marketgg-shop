package com.nhnacademy.marketgg.server.controller;

import com.nhnacademy.marketgg.server.cloud.StorageService;
import com.nhnacademy.marketgg.server.dto.response.ImageResponse;
import java.io.IOException;
import java.io.InputStream;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.http.HttpStatus;
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
    public ResponseEntity<byte[]> retrieveReviewDetails(
        @PathVariable(name = "assetNo") final Long assetId) throws IOException {

        ImageResponse imageResponse = storageService.retrieveImage(assetId);
        InputStream image = storageService.downloadObject("on7_storage", imageResponse.getName());

        byte[] bytes = IOUtils.toByteArray(image);

        return new ResponseEntity<>(bytes, HttpStatus.OK);
    }
}
