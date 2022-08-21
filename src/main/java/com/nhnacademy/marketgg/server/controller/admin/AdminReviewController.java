package com.nhnacademy.marketgg.server.controller.admin;

import com.nhnacademy.marketgg.server.dto.ShopResult;
import com.nhnacademy.marketgg.server.dto.response.common.CommonResponse;
import com.nhnacademy.marketgg.server.dto.response.common.SingleResponse;
import com.nhnacademy.marketgg.server.service.product.ReviewService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
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

    @Operation(summary = "베스트후기 선정",
               description = "선택한 후기를 베스트후기로 선정한다. 관리자만 가능하다.",
               parameters = {
                   @Parameter(name = "productId", description = "후기가 달려있는 상품의 상품번호", required = true),
                   @Parameter(name = "reviewId", description = "후기의 PK", required = true)
               },
               responses = @ApiResponse(responseCode = "200",
                                        content = @Content(mediaType = "application/json",
                                                           schema = @Schema(implementation = ShopResult.class)),
                                        useReturnTypeSchema = true))

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
