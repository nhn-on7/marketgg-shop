package com.nhnacademy.marketgg.server.service;

import com.nhnacademy.marketgg.server.dto.CategoryUpdateRequest;

public interface CategoryService {

    void updateCategory(Long categoryId, CategoryUpdateRequest categoryRequest);

}
