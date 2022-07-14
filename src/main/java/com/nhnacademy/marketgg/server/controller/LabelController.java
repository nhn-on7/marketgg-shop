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
@RequestMapping("/shop/v1/admin/labels")
@RequiredArgsConstructor
public class LabelController {

    private final LabelService labelService;

    private static final String DEFAULT_LABEL = "/shop/v1/admin/labels";

    @PostMapping
    ResponseEntity<Void> registerLabel(@RequestBody final LabelCreateRequest labelCreateRequest) {
        labelService.createLabel(labelCreateRequest);

        return ResponseEntity.status(HttpStatus.CREATED)
                             .location(URI.create(DEFAULT_LABEL))
                             .contentType(MediaType.APPLICATION_JSON)
                             .build();
    }

    @GetMapping
    ResponseEntity<List<LabelRetrieveResponse>> retrieveLabels() {
        List<LabelRetrieveResponse> labelResponse = labelService.retrieveLabels();

        return ResponseEntity.status(HttpStatus.OK)
                             .location(URI.create(DEFAULT_LABEL))
                             .body(labelResponse);
    }

    @DeleteMapping("/{labelId}")
    ResponseEntity<Void> deleteLabel(@PathVariable final Long labelId) {
        labelService.deleteLabel(labelId);

        return ResponseEntity.status(HttpStatus.OK)
                             .location(URI.create(DEFAULT_LABEL + "/" + labelId))
                             .build();
    }

}
