package com.nhnacademy.marketgg.server.controller;

import com.nhnacademy.marketgg.server.dto.response.CategorizationRetrieveResponse;
import com.nhnacademy.marketgg.server.service.CategorizationService;
import java.net.URI;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin/v1/categorizations")
@RequiredArgsConstructor
public class CategorizationController {

    private final CategorizationService categorizationService;

    @GetMapping
    public ResponseEntity<List<CategorizationRetrieveResponse>> retrieveCategorization() {
        List<CategorizationRetrieveResponse> categorizationResponses =
                categorizationService.retrieveCategorizations();

        return ResponseEntity.status(HttpStatus.OK)
                .location(URI.create("/admin/v1/categorizations"))
                .body(categorizationResponses);
    }

}
