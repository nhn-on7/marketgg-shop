package com.nhnacademy.marketgg.server.controller;

import com.nhnacademy.marketgg.server.dto.CategoryRegisterRequest;
import com.nhnacademy.marketgg.server.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin/v1/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;
    private final HttpHeaders headers;

    @PostMapping
    ResponseEntity<Void> createCategory(CategoryRegisterRequest categoryRequest) {

        categoryService.createCategory(categoryRequest);

        return ResponseEntity.status(HttpStatus.CREATED)
            .headers(headers)
            .contentType(MediaType.APPLICATION_JSON)
            .build();
    }

}
