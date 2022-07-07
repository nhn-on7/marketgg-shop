package com.nhnacademy.marketgg.server.service;

import com.nhnacademy.marketgg.server.dto.CategoryRequest;
import com.nhnacademy.marketgg.server.dto.CategoryResponse;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface CategoryService {

    void createCategory(CategoryRequest categoryRequest);

    List<CategoryResponse> retrieveCategories();

    void updateCategory(Long id, CategoryRequest categoryRequest);

    void deleteCategory(Long id);

}
