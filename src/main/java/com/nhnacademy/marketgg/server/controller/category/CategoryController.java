package com.nhnacademy.marketgg.server.controller.category;

import com.nhnacademy.marketgg.server.dto.ShopResult;
import com.nhnacademy.marketgg.server.dto.response.category.CategoryRetrieveResponse;
import com.nhnacademy.marketgg.server.service.category.CategoryService;
import java.net.URI;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    private static final String DEFAULT_CATEGORY = "/categories";

    /**
     * 전체 카테고리 목록을 조회하는 GET Mapping 을 지원합니다.
     *
     * @return 카테고리 전체 목록을 List 로 반환합니다.
     * @since 1.0.0
     */
    @GetMapping
    public ResponseEntity<ShopResult<List<CategoryRetrieveResponse>>> retrieveCategories() {
        List<CategoryRetrieveResponse> data = categoryService.retrieveCategories();

        return ResponseEntity.status(HttpStatus.OK)
                             .location(URI.create(DEFAULT_CATEGORY))
                             .body(ShopResult.successWith(data));
    }
}
