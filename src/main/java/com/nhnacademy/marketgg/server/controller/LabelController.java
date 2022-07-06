package com.nhnacademy.marketgg.server.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nhnacademy.marketgg.server.dto.LabelResponse;
import com.nhnacademy.marketgg.server.service.LabelService;
import java.net.URI;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin/v1/labels")
@RequiredArgsConstructor
public class LabelController {

    private final LabelService labelService;
    private final HttpHeaders headers = buildHeader();
    private final ObjectMapper mapper;

    @GetMapping
    ResponseEntity<List<LabelResponse>> retrieveLabels() throws JsonProcessingException {
        List<LabelResponse> labelResponses = labelService.retrieveLabels();

        headers.setLocation(URI.create("/admin/v1/labels"));
        return ResponseEntity.status(HttpStatus.OK)
            .headers(headers)
            .
            .body(mapper.writeValueAsString(labelResponses))

        return ResponseEntity.status(HttpStatus.CREATED)
                             .headers(headers)
                             .contentType(MediaType.APPLICATION_JSON)
                             .build();
    }

    private HttpHeaders buildHeader() {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);

        return httpHeaders;
    }

}
