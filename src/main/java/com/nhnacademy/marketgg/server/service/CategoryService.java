package com.nhnacademy.marketgg.server.service;

import com.nhnacademy.marketgg.server.dto.CategoryRetrieveResponse;

import java.util.List;

public interface CategoryService {

    List<CategoryRetrieveResponse> retrieveCategories();

}
