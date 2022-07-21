package com.nhnacademy.marketgg.server.controller;

import com.nhnacademy.marketgg.server.dto.response.CategorizationRetrieveResponse;
import com.nhnacademy.marketgg.server.service.CategorizationService;
import java.net.URI;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 카테고리 분류관리에 대한 RestController 입니다.
 *
 * @version 1.0.0
 */
@RestController
@RequestMapping("/shop/v1/admin/categorizations")
@RequiredArgsConstructor
public class CategorizationController {

    private final CategorizationService categorizationService;

    /**
     * 전체 카테고리 목록을 조회하는 GET Mapping 을 지원합니다.
     *
     * @return 카테고리 분류표의 전체 목록을 List 로 반환합니다.
     * @since 1.0.0
     */
    @GetMapping
    public ResponseEntity<List<CategorizationRetrieveResponse>> retrieveCategorization() {
        List<CategorizationRetrieveResponse> categorizationResponses =
                categorizationService.retrieveCategorizations();

        return ResponseEntity.status(HttpStatus.OK)
                .location(URI.create("/shop/v1/admin/categorizations"))
                .body(categorizationResponses);
    }

}
