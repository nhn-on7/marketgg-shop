package com.nhnacademy.marketgg.server.service;

import com.nhnacademy.marketgg.server.dto.CategoryUpdateRequest;

public interface CategoryService {

    void updateCategory(final Long categoryId, final CategoryUpdateRequest categoryRequest);

}
