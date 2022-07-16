package com.nhnacademy.marketgg.server.repository.category;

import com.nhnacademy.marketgg.server.dto.response.CategoryRetrieveResponse;
import com.nhnacademy.marketgg.server.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * 카테고리 레포지토리 입니다.
 *
 * @version 1.0.0
 */
public interface CategoryRepository extends JpaRepository<Category, String>, CategoryRepositoryCustom {

}
