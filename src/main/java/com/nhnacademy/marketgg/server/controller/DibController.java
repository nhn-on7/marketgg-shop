package com.nhnacademy.marketgg.server.controller;

import com.nhnacademy.marketgg.server.dto.response.DibRetrieveResponse;
import com.nhnacademy.marketgg.server.service.DibService;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 찜 관리에 관련된 Rest Controller 입니다.
 *
 * @version 1.0.0
 */
@RestController
@RequestMapping("/members")
@RequiredArgsConstructor
public class DibController {

    private static final String DEFAULT_DIB = "/members";

    private final DibService dibService;

    /**
     * 찜을 등록하는 POST Mapping 을 지원합니다.
     *
     * @param memberId  - 찜을 등록할 회원의 식별번호입니다.
     * @param productId - 찜으로 등록될 상품의 식별번호입니다.
     * @return Mapping URI 를 담은 응답 객체를 반환합니다.
     * @since 1.0.0
     */
    @PostMapping("/{memberId}/dibs/{productId}")
    public ResponseEntity<Void> createDib(@PathVariable final Long memberId, @PathVariable final Long productId) {
        dibService.createDib(memberId, productId);

        return ResponseEntity.status(HttpStatus.CREATED)
                             .location(URI.create(DEFAULT_DIB + "/" + memberId))
                             .contentType(MediaType.APPLICATION_JSON)
                             .build();
    }

    /**
     * 회원의 찜 목록을 조회하는 GET Mapping 을 지원합니다.
     *
     * @param memberId - 찜 목록을 조회하는 회원의 식별번호입니다.
     * @return 회원의 찜 목록을 List 로 반환합니다.
     * @since 1.0.0
     */
    @GetMapping("/{memberId}/dibs")
    public ResponseEntity<List<DibRetrieveResponse>> retrieveDibs(@PathVariable final Long memberId) {
        List<DibRetrieveResponse> dibResponses = dibService.retrieveDibs(memberId);

        return ResponseEntity.status(HttpStatus.OK)
                             .location(URI.create(DEFAULT_DIB + "/" + memberId))
                             .body(dibResponses);
    }

    /**
     * 찜을 삭제하는 DELETE Mapping 을 지원합니다.
     *
     * @param memberId  - 찜을 삭제하는 회원의 식별번호입니다.
     * @param productId - 찜 목록에서 삭제될 상품의 식별번호입니다.
     * @return Mapping URI 를 담은 응답 객체를 반환합니다.
     * @since 1.0.0
     */
    @DeleteMapping("/{memberId}/dibs/{productId}")
    public ResponseEntity<Void> deleteDib(@PathVariable final Long memberId, @PathVariable final Long productId) {
        dibService.deleteDib(memberId, productId);

        return ResponseEntity.status(HttpStatus.OK)
                             .location(URI.create(DEFAULT_DIB + "/" + memberId))
                             .contentType(MediaType.APPLICATION_JSON)
                             .build();
    }

}
