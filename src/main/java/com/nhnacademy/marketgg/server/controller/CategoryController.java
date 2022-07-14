package com.nhnacademy.marketgg.server.controller;

import com.nhnacademy.marketgg.server.dto.request.CategoryCreateRequest;
import com.nhnacademy.marketgg.server.dto.request.CategoryUpdateRequest;
import com.nhnacademy.marketgg.server.dto.response.CategoryRetrieveResponse;
import com.nhnacademy.marketgg.server.service.CategoryService;
import java.net.URI;
import java.util.List;
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

/**
 * 카테고리 관리에 관련된 RestController 입니다.
 *
 * @version  1.0.0
 */
@RestController
@RequestMapping("/admin/v1/categories")
@RequiredArgsConstructor
public class CategoryController {

    /**
     * 카테고리 서비스입니다.
     *
     * @since 1.0.0
     */
    private final CategoryService categoryService;

    /**
     * 입력한 정보로 카테고리를 생성하는 Mapping 을 지원합니다.
     *
     * @param categoryCreateRequest 카테고리를 생성하기 위한 DTO 입니다.
     * @return Mapping Uri 를 담은 응답 객체를 반환합니다.
     * @since 1.0.0
     */
    @PostMapping
    ResponseEntity<Void> createCategory(@RequestBody final CategoryCreateRequest categoryCreateRequest) {
        categoryService.createCategory(categoryCreateRequest);

        return ResponseEntity.status(HttpStatus.CREATED)
                             .location(URI.create("/admin/v1/categories"))
                             .contentType(MediaType.APPLICATION_JSON)
                             .build();
    }

    /**
     * 선택한 카테고리의 정보를 조회하는 Mapping 을 지원합니다.
     *
     * @param categoryId 조회할 카테고리의 식별 번호입니다.
     * @return 카테고리 한개의 정보를 담은 객체를 반환합니다.
     * @since 1.0.0
     */
    @GetMapping("/{categoryId}")
    public ResponseEntity<CategoryRetrieveResponse> retrieveCategory(@PathVariable String categoryId) {
        CategoryRetrieveResponse categoryResponse = categoryService.retrieveCategory(categoryId);

        return ResponseEntity.status(HttpStatus.OK)
                             .location(URI.create("/admin/v1/categories/" + categoryId))
                             .body(categoryResponse);
    }

    /**
     * 전체 카테고리 목록을 조회하는 Mapping 을 지원합니다.
     *
     * @return 카테고리 전체 목록을 List 로 반환합니다.
     * @since 1.0.0
     */
    @GetMapping
    public ResponseEntity<List<CategoryRetrieveResponse>> retrieveCategories() {
        List<CategoryRetrieveResponse> categoryResponses = categoryService.retrieveCategories();

        return ResponseEntity.status(HttpStatus.OK)
                             .location(URI.create("/admin/v1/categories"))
                             .body(categoryResponses);
    }

    /**
     * 입력한 정보로 선택한 카테고리 정보를 수정하는 Mapping 을 지원합니다.
     *
     * @param categoryId 수정할 카테고리의 식별번호입니다.
     * @param categoryRequest 카테고리를 수정하기 위한 DTO 입니다.
     * @return Mapping Uri 를 담은 응답 객체를 반환합니다.
     * @since 1.0.0
     */
    @PutMapping("/{categoryId}")
    public ResponseEntity<Void> updateCategory(@PathVariable final String categoryId,
                                               @RequestBody final CategoryUpdateRequest categoryRequest) {
        categoryService.updateCategory(categoryId, categoryRequest);

        return ResponseEntity.status(HttpStatus.OK)
                             .location(URI.create("/admin/v1/categories/" + categoryId))
                             .contentType(MediaType.APPLICATION_JSON)
                             .build();
    }

    /**
     * 선택한 카테고리를 삭제하는 Mapping 을 지원합니다.
     *
     * @param categoryId 삭제할 카테고리의 식별번호입니다.
     * @return Mapping Uri 를 담은 응답 객체를 반환합니다.
     * @since 1.0.0
     */
    @DeleteMapping("/{categoryId}")
    public ResponseEntity<Void> deleteCategory(@PathVariable final String categoryId) {
        categoryService.deleteCategory(categoryId);

        return ResponseEntity.status(HttpStatus.OK)
                             .location(URI.create("/admin/v1/categories/" + categoryId))
                             .contentType(MediaType.APPLICATION_JSON)
                             .build();
    }

}
