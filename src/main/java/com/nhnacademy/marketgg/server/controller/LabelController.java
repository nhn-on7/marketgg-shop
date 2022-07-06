package com.nhnacademy.marketgg.server.controller;

import com.nhnacademy.marketgg.server.dto.LabelDto;
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

    @GetMapping
    ResponseEntity<List<LabelDto>> retrieveLabels() {
        List<LabelDto> labelResponse = labelService.retrieveLabels();

        headers.setLocation(URI.create("/admin/v1/labels"));

        return ResponseEntity.status(HttpStatus.OK)
                             .headers(headers)
                             .body(labelResponse);
    }

    @PostMapping
    ResponseEntity<Void> registerLabel(@RequestBody LabelDto labelDto) {
        labelService.createLabel(labelDto);

        headers.setLocation(URI.create("/admin/v1/labels"));

        return ResponseEntity.status(HttpStatus.CREATED)
                             .headers(headers)
                             .contentType(MediaType.APPLICATION_JSON)
                             .build();
    }

    @DeleteMapping("/{labels-id}")
    ResponseEntity<Void> deleteLabel(@PathVariable("labels-id") Long id) {
        labelService.deleteLabel(id);

        headers.setLocation(URI.create("/admin/v1/labels/" + id));

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
