package com.nhnacademy.marketgg.server.controller;

import com.nhnacademy.marketgg.server.cloud.StorageService;
import com.nhnacademy.marketgg.server.dto.response.ImageResponse;
import com.nhnacademy.marketgg.server.entity.Asset;
import com.nhnacademy.marketgg.server.entity.Image;
import com.nhnacademy.marketgg.server.service.ImageService;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.client.ml.inference.preprocessing.Multi;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@RestController
@RequestMapping("/storage")
@RequiredArgsConstructor
public class StorageController {

    private final StorageService storageService;
    private final ImageService imageService;

    @GetMapping("/{assetNo}")
    public ResponseEntity<ImageResponse> retrieveImage(@PathVariable(name = "assetNo") final Long assetId)
        throws IOException {

        ImageResponse imageResponse = storageService.retrieveImage(assetId);
        // InputStream image = storageService.downloadObject("on7_storage", imageResponse.getName());
        //
        // byte[] bytes = IOUtils.toByteArray(image);

        return new ResponseEntity<>(imageResponse, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ImageResponse> uploadImage(@RequestBody final MultipartFile image) throws IOException {
        ImageResponse imageResponse = imageService.uploadImage(image);

        return new ResponseEntity<>(imageResponse, HttpStatus.OK);
    }

}
