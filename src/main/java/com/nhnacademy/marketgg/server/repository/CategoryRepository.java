package com.nhnacademy.marketgg.server.repository;

import com.nhnacademy.marketgg.server.dto.CategoryResponse;
import com.nhnacademy.marketgg.server.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    @Query("select c.categoryNo as categoryNo, c.name as name, c.code as code from Category c")
    List<CategoryResponse> findAllCategories();

}
