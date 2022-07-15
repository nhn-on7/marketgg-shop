package com.nhnacademy.marketgg.server.controller;

import com.nhnacademy.marketgg.server.dto.request.DibCreateRequest;
import com.nhnacademy.marketgg.server.dto.request.DibDeleteRequest;
import com.nhnacademy.marketgg.server.dto.response.DibRetrieveResponse;
import com.nhnacademy.marketgg.server.service.DibService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.List;

/**
 * 찜 관리에 관련된 Rest Controller 입니다.
 *
 * @version 1.0.0
 */
@RestController
@RequestMapping("/shop/v1/dibs")
@RequiredArgsConstructor
public class DibController {
    private final DibService dibService;
    private static final String DEFAULT_DIB = "/shop/v1/dibs";

    /**
     * 입력한 정보로 찜을 등록하는 Mapping 을 지원합니다.
     *
     * @param dibCreateRequest - 찜을 등록하기 위한 DTO 입니다.
     * @return Mapping URI 를 담은 응답 객체를 반환합니다.
     * @since 1.0.0
     */
    @PostMapping
    ResponseEntity<Void> createDib(@RequestBody final DibCreateRequest dibCreateRequest) {
        dibService.createDib(dibCreateRequest);

        return ResponseEntity.status(HttpStatus.CREATED)
                             .location(URI.create(DEFAULT_DIB))
                             .contentType(MediaType.APPLICATION_JSON)
                             .build();
    }

    /**
     * 회원의 찜 목록을 조회하는 Mapping 을 지원합니다.
     *
     * @param memberId - 회원의 회원번호 입니다.
     * @return 회원의 찜 목록을 List 로 반환합니다.
     * @since 1.0.0
     */
    @GetMapping("/{memberId}")
    ResponseEntity<List<DibRetrieveResponse>> retrieveDibs(@PathVariable final Long memberId) {
        List<DibRetrieveResponse> dibResponses = dibService.retrieveDibs(memberId);

        return ResponseEntity.status(HttpStatus.OK)
                             .location(URI.create(DEFAULT_DIB + "/" + memberId))
                             .body(dibResponses);
    }

    /**
     * 선택한 찜을 삭제하는 Mapping 을 지원합니다.
     *
     * @param dibDeleteRequest - 삭제할 찜의 회원번호와 상품번호가 있는 DTO 입니다.
     * @return Mapping URI 를 담은 응답 객체를 반환합니다.
     */
    @DeleteMapping
    ResponseEntity<Void> deleteDib(@RequestBody final DibDeleteRequest dibDeleteRequest) {
        dibService.deleteDib(dibDeleteRequest);

        return ResponseEntity.status(HttpStatus.OK)
                .location(URI.create(DEFAULT_DIB))
                .contentType(MediaType.APPLICATION_JSON)
                .build();
    }

}
