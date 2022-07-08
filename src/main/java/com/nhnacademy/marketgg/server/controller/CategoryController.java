package com.nhnacademy.marketgg.server.controller;

import com.nhnacademy.marketgg.server.dto.request.CategoryCreateRequest;
import com.nhnacademy.marketgg.server.dto.response.CategoryRetrieveResponse;
import com.nhnacademy.marketgg.server.dto.request.CategoryUpdateRequest;
import com.nhnacademy.marketgg.server.service.CategoryService;
import lombok.RequiredArgsConstructor;
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

    @PostMapping
    ResponseEntity<Void> createCategory(@RequestBody final CategoryCreateRequest categoryCreateRequest) {
        categoryService.createCategory(categoryCreateRequest);

        return ResponseEntity.status(HttpStatus.CREATED)
                             .location(URI.create("/admin/v1/categories"))
                             .contentType(MediaType.APPLICATION_JSON)
                             .build();
    }

    @GetMapping
    ResponseEntity<List<CategoryRetrieveResponse>> retrieveCategories() {
        List<CategoryRetrieveResponse> categoryResponses = categoryService.retrieveCategories();

        return ResponseEntity.status(HttpStatus.OK)
                             .location(URI.create("/admin/v1/categories"))
                             .body(categoryResponses);
    }

    @PutMapping("/{categoryId}")
    ResponseEntity<Void> updateCategory(@PathVariable final Long categoryId,
                                        @RequestBody final CategoryUpdateRequest categoryRequest) {
        categoryService.updateCategory(categoryId, categoryRequest);

        return ResponseEntity.status(HttpStatus.OK)
                             .location(URI.create("/admin/v1/categories/" + categoryId))
                             .contentType(MediaType.APPLICATION_JSON)
                             .build();
    }

    @DeleteMapping("/{categoryId}")
    ResponseEntity<Void> deleteCategory(@PathVariable final Long categoryId) {
        categoryService.deleteCategory(categoryId);

        return ResponseEntity.status(HttpStatus.OK)
                             .location(URI.create("/admin/v1/categories/" + categoryId))
                             .contentType(MediaType.APPLICATION_JSON)
                             .build();
    }

}
