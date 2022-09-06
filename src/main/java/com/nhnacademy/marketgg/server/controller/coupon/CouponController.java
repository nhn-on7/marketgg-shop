package com.nhnacademy.marketgg.server.controller.coupon;

import com.nhnacademy.marketgg.server.dto.PageEntity;
import com.nhnacademy.marketgg.server.dto.ShopResult;
import com.nhnacademy.marketgg.server.dto.request.DefaultPageRequest;
import com.nhnacademy.marketgg.server.dto.request.coupon.CouponDto;
import com.nhnacademy.marketgg.server.service.coupon.CouponService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 활성화된 쿠폰을 조회하는 RestController 입니다.
 *
 * @version 1.0.0
 */
@RestController
@RequestMapping("/market-coupons")
@RequiredArgsConstructor
public class CouponController {

    private final CouponService couponService;

    /**
     * 활성화된 쿠폰을 조회하는 GetMapping 을 지원합니다.
     *
     * @return 활성화된 쿠폰 목록을 가진 DTO 객체를 반환합니다.
     * @author 민아영
     * @since 1.0.0
     */
    @Operation(summary = "활성화된 쿠폰 조회",
               description = "활성화된 쿠폰을 조회합니다.",
               responses = @ApiResponse(responseCode = "200",
                                        content = @Content(mediaType = "application/json",
                                                           schema = @Schema(implementation = ShopResult.class)),
                                        useReturnTypeSchema = true))
    @GetMapping
    public ResponseEntity<ShopResult<PageEntity<CouponDto>>> retrieveGivenCoupons(@RequestParam(value = "page",
                                                                                                defaultValue = "1") final Integer page) {

        DefaultPageRequest pageRequest = new DefaultPageRequest(page - 1);

        PageEntity<CouponDto> coupons
            = couponService.retrieveActivateCoupons(pageRequest.getPageable());

        return ResponseEntity.status(HttpStatus.OK)
                             .contentType(MediaType.APPLICATION_JSON)
                             .body(ShopResult.successWith(coupons));
    }

}
