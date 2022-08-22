package com.nhnacademy.marketgg.server.controller.admin;

import com.nhnacademy.marketgg.server.dto.PageEntity;
import com.nhnacademy.marketgg.server.dto.ShopResult;
import com.nhnacademy.marketgg.server.dto.request.coupon.CouponDto;
import com.nhnacademy.marketgg.server.service.coupon.CouponService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
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
 * 쿠폰 관련 Rest Controller 입니다.
 *
 * @author 민아영
 * @author 김정민
 * @since 1.0.0
 */
@RestController
@RequestMapping("/admin/coupons")
@RequiredArgsConstructor
public class AdminCouponController {

    private final CouponService couponService;

    /**
     * 입력한 정보로 쿠폰을 등록하는 PostMapping 을 지원합니다.
     *
     * @param couponDto - 쿠폰을 등록하기 위한 정보를 담고 있는 DTO 입니다.
     * @return Mapping URI 를 담은 응답 객체를 반환합니다.
     * @author 민아영
     * @author 김정민
     * @since 1.0.0
     */
    @Operation(summary = "쿠폰 등록",
               description = "관리자가 쿠폰 정보를 입력하여 새로운 쿠폰을 등록합니다.",
               parameters = @Parameter(name = "couponDto", description = "등록할 쿠폰 요청 객체", required = true),
               responses = @ApiResponse(responseCode = "201",
                                        content = @Content(mediaType = "application/json",
                                                           schema = @Schema(implementation = ShopResult.class)),
                                        useReturnTypeSchema = true))
    @PostMapping
    public ResponseEntity<ShopResult<Void>> createCoupon(@Valid @RequestBody final CouponDto couponDto) {
        couponService.createCoupon(couponDto);

        return ResponseEntity.status(HttpStatus.CREATED)
                             .contentType(MediaType.APPLICATION_JSON)
                             .body(ShopResult.successWithDefaultMessage());
    }

    /**
     * couponId 에 해당하는 쿠폰을 조회하는 GetMapping 을 지원합니다.
     *
     * @param couponId - 조회할 쿠폰의 식별번호 입니다.
     * @return 조회한 쿠폰의 정보를 담은 객체를 반환합니다.
     * @author 민아영
     * @author 김정민
     * @since 1.0.0
     */
    @Operation(summary = "쿠폰 단건 조회",
               description = "관리자가 조회할 쿠폰 번호를 입력하여 쿠폰을 조회합니다.",
               parameters = @Parameter(name = "couponId", description = "조회할 쿠폰 번호", required = true),
               responses = @ApiResponse(responseCode = "200",
                                        content = @Content(mediaType = "application/json",
                                                           schema = @Schema(implementation = ShopResult.class)),
                                        useReturnTypeSchema = true))
    @GetMapping("/{couponId}")
    public ResponseEntity<ShopResult<CouponDto>> retrieveCoupon(@PathVariable final Long couponId) {
        CouponDto couponResponse = couponService.retrieveCoupon(couponId);

        return ResponseEntity.status(HttpStatus.OK)
                             .contentType(MediaType.APPLICATION_JSON)
                             .body(ShopResult.successWith(couponResponse));
    }

    /**
     * 전체 쿠폰 목록을 조회하는 GetMapping 을 지원합니다.
     *
     * @param pageable 조회하려는 페이지 정보입니다.
     *                 (@PageableDefault - 기본값과 추가 설정을 할 수 있습니다.)
     * @return 전체 쿠폰 목록 DTO 를 List 로 반환합니다.
     * @author 민아영
     * @author 김정민
     * @since 1.0.0
     */
    @Operation(summary = "쿠폰 전체 조회",
               description = "관리자가 등록되어 있는 쿠폰 목록을 전체 조회합니다.",
               responses = @ApiResponse(responseCode = "200",
                                        content = @Content(mediaType = "application/json",
                                                           schema = @Schema(implementation = ShopResult.class)),
                                        useReturnTypeSchema = true))
    @GetMapping
    public ResponseEntity<ShopResult<PageEntity<CouponDto>>> retrieveCoupons(@PageableDefault final Pageable pageable) {
        PageEntity<CouponDto> couponResponses = couponService.retrieveCoupons(pageable);

        return ResponseEntity.status(HttpStatus.OK)
                             .contentType(MediaType.APPLICATION_JSON)
                             .body(ShopResult.successWith(couponResponses));
    }

    /**
     * 선택한 쿠폰을 수정하는 PutMapping 을 지원합니다.
     *
     * @param couponId  - 수정할 쿠폰의 식별번호입니다.
     * @param couponDto - 수정할 정보를 담은 DTO 입니다.
     * @return Mapping URI 를 담은 응답 객체를 반환합니다.
     * @author 민아영
     * @author 김정민
     * @since 1.0.0
     */
    @Operation(summary = "쿠폰 수정",
               description = "관리자가 수정할 쿠폰 정보를 입력하여 기존 쿠폰을 수정합니다.",
               parameters = {@Parameter(name = "couponId", description = "수정할 쿠폰 번호", required = true),
                   @Parameter(name = "couponDto", description = "수정 내용을 담은 쿠폰 요청 객체", required = true)},
               responses = @ApiResponse(responseCode = "200",
                                        content = @Content(mediaType = "application/json",
                                                           schema = @Schema(implementation = ShopResult.class)),
                                        useReturnTypeSchema = true))
    @PutMapping("/{couponId}")
    public ResponseEntity<ShopResult<Void>> updateCoupon(@PathVariable final Long couponId,
                                                         @Valid @RequestBody final CouponDto couponDto) {

        couponService.updateCoupon(couponId, couponDto);

        return ResponseEntity.status(HttpStatus.OK)
                             .contentType(MediaType.APPLICATION_JSON)
                             .body(ShopResult.successWithDefaultMessage());
    }

    /**
     * 선택한 쿠폰을 삭제하는 DeleteMapping 을 지원합니다.
     *
     * @param couponId - 삭제할 쿠폰의 식별번호입니다.
     * @return Mapping URI 를 담은 응답 객체를 반환합니다.
     * @author 민아영
     * @author 김정민
     * @since 1.0.0
     */
    @Operation(summary = "쿠폰 삭제",
               description = "관리자가 쿠폰 번호를 입력하여 쿠폰을 삭제합니다.",
               parameters = @Parameter(name = "couponId", description = "삭제할 쿠폰 번호", required = true),
               responses = @ApiResponse(responseCode = "204",
                                        content = @Content(mediaType = "application/json",
                                                           schema = @Schema(implementation = ShopResult.class)),
                                        useReturnTypeSchema = true))
    @DeleteMapping("/{couponId}")
    public ResponseEntity<ShopResult<Void>> deleteCoupon(@PathVariable final Long couponId) {
        couponService.deleteCoupon(couponId);

        return ResponseEntity.status(HttpStatus.NO_CONTENT)
                             .contentType(MediaType.APPLICATION_JSON)
                             .body(ShopResult.successWithDefaultMessage());
    }

}
