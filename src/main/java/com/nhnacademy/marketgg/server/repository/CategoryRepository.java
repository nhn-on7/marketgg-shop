package com.nhnacademy.marketgg.server.repository;

import com.nhnacademy.marketgg.server.dto.response.CategoryRetrieveResponse;
import com.nhnacademy.marketgg.server.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, String> {

    @Query("SELECT c.categoryCode as categoryCode, cz.name as categorizationName, c.name as categoryName, c.sequence as sequence " +
            "FROM Category c " +
            "   INNER JOIN Categorization cz " +
            "   ON cz.categorizationCode = c.categorization.categorizationCode " +
            "WHERE c.categoryCode = :categoryCode")
    CategoryRetrieveResponse findByCode(final String categoryCode);

    @Query("SELECT c.categoryCode as categoryCode, cz.name as categorizationName, c.name as categoryName, c.sequence as sequence " +
            "FROM Category c " +
            "   INNER JOIN Categorization cz " +
            "   ON c.categorization.categorizationCode = cz.categorizationCode")
    List<CategoryRetrieveResponse> findAllCategories();

}
