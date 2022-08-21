package com.nhnacademy.marketgg.server.controller.product;

import com.nhnacademy.marketgg.server.annotation.Auth;
import com.nhnacademy.marketgg.server.dto.ShopResult;
import com.nhnacademy.marketgg.server.dto.info.MemberInfo;
import com.nhnacademy.marketgg.server.dto.request.DefaultPageRequest;
import com.nhnacademy.marketgg.server.dto.request.review.ReviewCreateRequest;
import com.nhnacademy.marketgg.server.dto.request.review.ReviewUpdateRequest;
import com.nhnacademy.marketgg.server.dto.response.review.ReviewResponse;
import com.nhnacademy.marketgg.server.service.product.ReviewService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import java.io.IOException;
import java.net.URI;
import java.util.List;
import java.util.Objects;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * 리뷰 관리를 위한 컨트롤러입니다.
 *
 * @author 조현진
 * @version 1.0.0
 */
@Slf4j
@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ReviewController {

    private static final String DEFAULT_REVIEW_URI = "/products/";
    private static final String REVIEW_PATH = "/reviews/";

    private final ReviewService reviewService;

    /**
     * 리뷰를 생성합니다. 추후 사진 기능이 추가될 예정입니다.
     *
     * @param productId     - 후기가 달릴 상품의 PK입니다.
     *                      <<<<<<< HEAD
     *                      =======
     * @param memberInfo    - 후기를 작성한 회원의 정보입니다.
     *                      >>>>>>> 704b1c34 (Fix: typo)
     * @param reviewRequest - 후기 생성을 위한 dto 입니다.
     * @param bindingResult - validation 적용을 위한 파라미터입니다.
     * @param images        - 후기 생성시 첨부된 사진들입니다.
     * @return Void를 담은 응답객체를 반환합니다.
     */

    @Operation(summary = "회원이 후기를 작성할 때 필요한 api입니다.",
               description = "후기 생성에 관한 정보를 받고, 데이터베이스에 해당 정보를 영속화합니다.",
               parameters = {
                   @Parameter(name = "productId", description = "후기가 달린 상품의 상품번호", required = true),
                   @Parameter(name = "memberInfo", description = "후기를 작성한 회원 정보", required = true),
                   @Parameter(name = "reviewRequest", description = "DB에 영속화될 후기의 내용", required = true),
                   @Parameter(name = "image", description = "후기에 보일 이미지", required = true) },
               responses = @ApiResponse(responseCode = "201",
                                        content = @Content(mediaType = "application/json",
                                                           schema = @Schema(implementation = ShopResult.class)),
                                        useReturnTypeSchema = true))
    @Auth
    @PostMapping(value = "/{productId}/reviews", consumes = { MediaType.APPLICATION_JSON_VALUE,
        MediaType.MULTIPART_FORM_DATA_VALUE })
    public ResponseEntity<ShopResult<Void>> createReview(@PathVariable final Long productId,
                                                         final MemberInfo memberInfo,
                                                         @RequestPart @Valid final ReviewCreateRequest reviewRequest,
                                                         BindingResult bindingResult,
                                                         @RequestPart(required = false) MultipartFile images)
        throws IOException {

        if (bindingResult.hasErrors()) {
            throw new IllegalArgumentException(bindingResult.getAllErrors().get(0).getDefaultMessage());
        }

        ResponseEntity<ShopResult<Void>> returnResponseEntity =
            ResponseEntity.status(HttpStatus.CREATED)
                          .location(URI.create(DEFAULT_REVIEW_URI + productId + REVIEW_PATH))
                          .contentType(MediaType.APPLICATION_JSON)
                          .body(ShopResult.success());

        if (Objects.isNull(images)) {
            reviewService.createReview(reviewRequest, memberInfo);

            return returnResponseEntity;
        }

        reviewService.createReview(reviewRequest, images, memberInfo);

        return returnResponseEntity;
    }

    /**
     * 모든 리뷰를 조회합니다.
     *
     * @param productId - 리뷰가 달린 상품의 기본키입니다.
     * @return - 페이지 정보가 담긴 공통 응답 객체를 반환합니다.
     */

    @Operation(summary = "후기 목록 전체 조회",
               description = "상품 상세페이지 하단에서 보여야하는 후기들의 목록입니다.",
               parameters = {
                   @Parameter(name = "productId", description = "후기가 달려있는 상품의 상품번호", required = true),
                   @Parameter(name = "page", description = "현재 페이지. 기본값은 0", required = true)
               },
               responses = @ApiResponse(responseCode = "200",
                                        content = @Content(mediaType = "application/json",
                                                           schema = @Schema(implementation = ShopResult.class)),
                                        useReturnTypeSchema = true))

    @GetMapping("/{productId}/reviews")
    public ResponseEntity<ShopResult<List<ReviewResponse>>> retrieveReviews(@PathVariable final Long productId,
                                                                            @RequestParam(value = "page", defaultValue = "0")
                                                                            final Integer page) {

        DefaultPageRequest pageRequest = new DefaultPageRequest(page);
        List<ReviewResponse> reviewResponses = reviewService.retrieveReviews(pageRequest.getPageable());

        return ResponseEntity.status(HttpStatus.OK)
                             .location(URI.create(DEFAULT_REVIEW_URI + productId + "/review"))
                             .contentType(MediaType.APPLICATION_JSON)
                             .body(ShopResult.success(reviewResponses));
    }

    /**
     * 후기의 상세정보를 조회합니다.
     *
     * @param productId - 후기가 달린 상품의 기본키입니다.
     * @param reviewId  - 작성된 후기의 기본키입니다.
     * @return - ReviewResponse가 담긴 공통 응답객체를 반환합니다.
     */

    @Operation(summary = "후기 상세 조회",
               description = "후기를 클릭하면 보이는 후기의 상세 정보입니다.",
               parameters = {
                   @Parameter(name = "productId", description = "후기가 달려있는 상품의 상품번호", required = true),
                   @Parameter(name = "reviewId", description = "후기의 PK", required = true)
               },
               responses = @ApiResponse(responseCode = "200",
                                        content = @Content(mediaType = "application/json",
                                                           schema = @Schema(implementation = ShopResult.class)),
                                        useReturnTypeSchema = true))
    @GetMapping("/{productId}/reviews/{reviewId}")
    public ResponseEntity<ShopResult<ReviewResponse>> retrieveReviewDetails(@PathVariable final Long productId,
                                                                            @PathVariable final Long reviewId) {

        ReviewResponse reviewResponse = reviewService.retrieveReviewDetails(reviewId);

        return ResponseEntity.status(HttpStatus.OK)
                             .location(URI.create(DEFAULT_REVIEW_URI + productId + REVIEW_PATH + reviewId))
                             .contentType(MediaType.APPLICATION_JSON)
                             .body(ShopResult.success(reviewResponse));
    }

    /**
     * 후기를 수정합니다.
     *
     * @param productId     - 후기가 달린 상품의 기본키입니다.
     * @param reviewId      - 후기의 식별번호입니다.
     * @param reviewRequest - 후기 수정 정보가 담긴 DTO 입니다.
     * @param bindingResult - validation을 체크합니다.
     * @return - Void 타입 응답객체를 반환합니다.
     */

    @Operation(summary = "회원이 후기를 수정할 때 필요한 api입니다.",
               description = "후기 수정에 관한 정보를 받고, 데이터베이스에 해당 정보를 영속화합니다.",
               parameters = {
                   @Parameter(name = "productId", description = "후기가 달린 상품의 상품번호", required = true),
                   @Parameter(name = "memberInfo", description = "후기를 작성한 회원 정보", required = true),
                   @Parameter(name = "reviewRequest", description = "DB에 영속화될 후기의 내용", required = true),
               },
               responses = @ApiResponse(responseCode = "201",
                                        content = @Content(mediaType = "application/json",
                                                           schema = @Schema(implementation = ShopResult.class)),
                                        useReturnTypeSchema = true))
    @Auth
    @PutMapping("/{productId}/reviews/{reviewId}")
    public ResponseEntity<ShopResult<Void>> updateReview(@PathVariable final Long productId,
                                                         @PathVariable final Long reviewId,
                                                         @RequestBody @Valid final ReviewUpdateRequest reviewRequest,
                                                         BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            throw new IllegalArgumentException(bindingResult.getAllErrors().get(0).getDefaultMessage());
        }

        reviewService.updateReview(reviewRequest, reviewId);

        return ResponseEntity.status(HttpStatus.OK)
                             .location(URI.create(DEFAULT_REVIEW_URI + productId + REVIEW_PATH + reviewId))
                             .contentType(MediaType.APPLICATION_JSON)
                             .body(ShopResult.success());
    }

    /**
     * 후기를 삭제합니다.
     *
     * @param productId - 후기가 달린 상품의 기본키입니다.
     * @param reviewId  - 후기의 식별번호 입니다.
     * @return - Void 타입 응답객체를 반환합니다.
     */
    @DeleteMapping("/{productId}/reviews/{reviewId}")
    public ResponseEntity<ShopResult<Void>> deleteReview(@PathVariable final Long productId,
                                                         @PathVariable final Long reviewId) {
        reviewService.deleteReview(reviewId);

        return ResponseEntity.status(HttpStatus.OK)
                             .location(URI.create(DEFAULT_REVIEW_URI + productId + REVIEW_PATH + reviewId))
                             .contentType(MediaType.APPLICATION_JSON)
                             .body(ShopResult.success());
    }

}
