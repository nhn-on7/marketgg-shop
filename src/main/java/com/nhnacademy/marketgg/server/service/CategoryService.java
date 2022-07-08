package com.nhnacademy.marketgg.server.service;

import com.nhnacademy.marketgg.server.dto.CategoryRequest;
import com.nhnacademy.marketgg.server.dto.CategoryResponse;

import java.util.List;

public interface CategoryService {

    void createCategory(CategoryRequest categoryRequest);

}
