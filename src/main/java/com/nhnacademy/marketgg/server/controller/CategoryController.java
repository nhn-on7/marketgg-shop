package com.nhnacademy.marketgg.server.controller;

import com.nhnacademy.marketgg.server.dto.CategoryRequest;
import com.nhnacademy.marketgg.server.dto.CategoryResponse;
import com.nhnacademy.marketgg.server.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/admin/v1/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;
    private final HttpHeaders headers = buildHttpHeader();

    @PostMapping
    ResponseEntity<Void> createCategory(@RequestBody final CategoryRequest categoryRequest) {

        categoryService.createCategory(categoryRequest);

        headers.setLocation(URI.create("/admin/v1/categories"));

        return ResponseEntity.status(HttpStatus.CREATED)
                             .headers(headers)
                             .contentType(MediaType.APPLICATION_JSON)
                             .build();
    }

    @GetMapping
    ResponseEntity<List<CategoryResponse>> retrieveCategories() {
        List<CategoryResponse> categoryResponses = categoryService.retrieveCategories();

        headers.setLocation(URI.create("/admin/v1/categories"));

        return ResponseEntity.status(HttpStatus.OK)
                             .headers(headers)
                             .body(categoryResponses);
    }

    @PutMapping("/{categoryId}")
    ResponseEntity<Void> updateCategory(@PathVariable final Long categoryId,
                                        @RequestBody final CategoryRequest categoryRequest) {

        categoryService.updateCategory(categoryId, categoryRequest);

        headers.setLocation(URI.create("/admin/v1/categories/" + categoryId));

        return ResponseEntity.status(HttpStatus.OK)
                             .headers(headers)
                             .contentType(MediaType.APPLICATION_JSON)
                             .build();
    }

    @DeleteMapping("/{categoryId}")
    ResponseEntity<Void> deleteCategory(@PathVariable final Long categoryId) {
        categoryService.deleteCategory(categoryId);

        headers.setLocation(URI.create("/admin/v1/categories/" + categoryId));

        return ResponseEntity.status(HttpStatus.OK)
                             .headers(headers)
                             .build();
    }

    private HttpHeaders buildHttpHeader() {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);

        return httpHeaders;
    }

}
