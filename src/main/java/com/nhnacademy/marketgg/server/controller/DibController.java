package com.nhnacademy.marketgg.server.controller;

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
@RequestMapping("/shop/v1/members")
@RequiredArgsConstructor
public class DibController {
    private final DibService dibService;
    private static final String DEFAULT_DIB = "/shop/v1/members";

//here
    @PostMapping("/{memberId}/dibs/{productId}")
    ResponseEntity<Void> createDib(@PathVariable final Long memberId,
                                   @PathVariable final Long productId) {

        dibService.createDib(memberId, productId);

        return ResponseEntity.status(HttpStatus.CREATED)
                             .location(URI.create(DEFAULT_DIB + "/" + memberId))
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
    @GetMapping("/{memberId}/dibs")
    ResponseEntity<List<DibRetrieveResponse>> retrieveDibs(@PathVariable final Long memberId) {
        List<DibRetrieveResponse> dibResponses = dibService.retrieveDibs(memberId);

        return ResponseEntity.status(HttpStatus.OK)
                             .location(URI.create(DEFAULT_DIB + "/" + memberId))
                             .body(dibResponses);
    }

    //here
    @DeleteMapping("/{memberId}/dibs/{productId}")
    ResponseEntity<Void> deleteDib(@PathVariable final Long memberId,
                                   @PathVariable final Long productId) {
        dibService.deleteDib(memberId, productId);

        return ResponseEntity.status(HttpStatus.OK)
                             .location(URI.create(DEFAULT_DIB + "/" + memberId))
                             .contentType(MediaType.APPLICATION_JSON)
                             .build();
    }

}
