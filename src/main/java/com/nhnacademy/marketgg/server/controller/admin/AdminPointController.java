package com.nhnacademy.marketgg.server.controller.admin;

import com.nhnacademy.marketgg.server.dto.response.PointRetrieveResponse;
import com.nhnacademy.marketgg.server.service.PointService;
import java.net.URI;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 관리자 관련 포인트 내역 관리를 할 수 있는 Mapping 을 지원합니다.
 *
 * @version 1.0.0
 */
@RestController
@RequestMapping("/admin/points")
@RequiredArgsConstructor
public class AdminPointController {

    private final PointService pointService;

    private static final String DEFAULT_ADMIN = "/admin";

    /**
     * 전체 회원의 포인트 내역을 반환합니다.
     *
     * @return 전체 회원의 포인트 내역을 List 로 반환합니다.
     * @since 1.0.0
     */
    @GetMapping
    public ResponseEntity<List<PointRetrieveResponse>> adminRetrievePointHistory() {
        List<PointRetrieveResponse> responses = pointService.adminRetrievePointHistories();

        return ResponseEntity.status(HttpStatus.OK)
                .location(URI.create(DEFAULT_ADMIN + "/points"))
                .body(responses);
    }

}
