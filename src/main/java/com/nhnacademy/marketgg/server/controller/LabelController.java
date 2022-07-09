package com.nhnacademy.marketgg.server.controller;

import com.nhnacademy.marketgg.server.dto.request.LabelCreateRequest;
import com.nhnacademy.marketgg.server.dto.response.LabelRetrieveResponse;
import com.nhnacademy.marketgg.server.service.LabelService;

import java.net.URI;
import java.util.List;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/v1/labels")
@RequiredArgsConstructor
public class LabelController {

    private final LabelService labelService;
    private final HttpHeaders headers = buildHeader();

    @PostMapping
    ResponseEntity<Void> registerLabel(@RequestBody final LabelCreateRequest labelCreateRequest) {
        labelService.createLabel(labelCreateRequest);

        headers.setLocation(URI.create("/admin/v1/labels"));

        return ResponseEntity.status(HttpStatus.CREATED)
                             .headers(headers)
                             .contentType(MediaType.APPLICATION_JSON)
                             .build();
    }

    @GetMapping
    ResponseEntity<List<LabelRetrieveResponse>> retrieveLabels() {
        List<LabelRetrieveResponse> labelResponse = labelService.retrieveLabels();

        headers.setLocation(URI.create("/admin/v1/labels"));

        return ResponseEntity.status(HttpStatus.OK)
                             .headers(headers)
                             .body(labelResponse);
    }

    @DeleteMapping("/{labelId}")
    ResponseEntity<Void> deleteLabel(@PathVariable final Long labelId) {
        labelService.deleteLabel(labelId);

        headers.setLocation(URI.create("/admin/v1/labels/" + labelId));

        return ResponseEntity.status(HttpStatus.OK)
                             .headers(headers)
                             .build();
    }

    private HttpHeaders buildHeader() {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);

        return httpHeaders;
    }

}
