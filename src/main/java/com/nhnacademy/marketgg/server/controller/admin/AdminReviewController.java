package com.nhnacademy.marketgg.server.controller.admin;

import com.nhnacademy.marketgg.server.dto.response.common.CommonResponse;
import com.nhnacademy.marketgg.server.dto.response.common.SingleResponse;
import com.nhnacademy.marketgg.server.service.product.ReviewService;
import java.net.URI;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/admin/products")
@RequiredArgsConstructor
public class AdminReviewController {

    private final ReviewService reviewService;
    private static final String DEFAULT_REVIEW_URI = "/admin/products/";

    // @RoleCheck(accessLevel = Role.ROLE_ADMIN)
    @PostMapping("/{productId}/reviews/{reviewId}/make-best")
    public ResponseEntity<CommonResponse> makeBestReview(@PathVariable final Long productId,
                                                         @PathVariable final Long reviewId) {

        SingleResponse<Boolean> response = reviewService.makeBestReview(reviewId);
        log.info("다음의 상품이 베스트후기로 선정되었습니다:{}", productId);

        return ResponseEntity.status(HttpStatus.OK)
                             .location(URI.create(
                                 DEFAULT_REVIEW_URI + productId + "/reviews/" + reviewId + "/makeBest"))
                             .contentType(MediaType.APPLICATION_JSON)
                             .body(response);
    }

}
