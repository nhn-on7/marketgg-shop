package com.nhnacademy.marketgg.server.controller;

import com.nhnacademy.marketgg.server.dto.CategoryCreateRequest;
import com.nhnacademy.marketgg.server.service.CategoryService;
import java.net.URI;
import lombok.RequiredArgsConstructor;
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

    @PostMapping
    ResponseEntity<Void> createCategory(CategoryCreateRequest categoryCreateRequest) {

        categoryService.createCategory(categoryCreateRequest);

        return ResponseEntity.status(HttpStatus.CREATED)
                .location(URI.create("/admin/v1/categories"))
                .contentType(MediaType.APPLICATION_JSON)
                .build();
    }
    
}
