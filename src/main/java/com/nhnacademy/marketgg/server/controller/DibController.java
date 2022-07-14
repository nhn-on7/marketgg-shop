package com.nhnacademy.marketgg.server.controller;

import com.nhnacademy.marketgg.server.dto.request.DibCreateRequest;
import com.nhnacademy.marketgg.server.service.DibService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequestMapping("/shop/v1/dibs")
@RequiredArgsConstructor
public class DibController {

    private final DibService dibService;

    @PostMapping
    ResponseEntity<Void> createDib(@RequestBody final DibCreateRequest dibCreateRequest) {
        dibService.createDib(dibCreateRequest);

        return ResponseEntity.status(HttpStatus.CREATED)
                             .location(URI.create("/shop/v1/dibs"))
                             .contentType(MediaType.APPLICATION_JSON)
                             .build();
    }

}
