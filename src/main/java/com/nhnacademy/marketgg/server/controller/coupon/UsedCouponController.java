package com.nhnacademy.marketgg.server.controller.coupon;

import com.nhnacademy.marketgg.server.dto.ShopResult;
import com.nhnacademy.marketgg.server.dto.request.coupon.UsedCouponDto;
import com.nhnacademy.marketgg.server.service.coupon.UsedCouponService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 사용 쿠폰에 관련된 RestController 입니다.
 * 회원이 주문을 완료하면 사용 쿠폰이 생성됩니다.
 * 회원이 주문을 취소하면 사용 쿠폰이 삭제됩니다.
 *
 * @version 1.0.0
 */
@RestController
@RequestMapping("/system/used-coupons")
@RequiredArgsConstructor
public class UsedCouponController {

    private final UsedCouponService usedCouponService;

    /**
     * 주문번호, 회원번호, 쿠폰번호를 DTO 에 담아서 오면 사용 쿠폰을 생성하는 PostMapping 을 지원합니다.
     *
     * @param usedCouponDto - 사용 쿠폰에 등록될 DTO 입니다.
     * @return Mapping URI 를 담은 응답 객체를 반환합니다.
     * @since 1.0.0
     */
    @Operation(summary = "사용 쿠폰 생성",
        description = "쿠폰 사용이 완료되면 사용 쿠폰이 생성됩니다.",
        parameters = @Parameter(name = "usedCouponDto", description = "생성할 사용 쿠폰 요청 객체", required = true),
        responses = @ApiResponse(responseCode = "201",
            content = @Content(mediaType = "application/json",
                schema = @Schema(implementation = ShopResult.class)),
            useReturnTypeSchema = true))
    @PostMapping
    public ResponseEntity<ShopResult<Void>> createUsedCoupons(@Valid @RequestBody final UsedCouponDto usedCouponDto) {
        usedCouponService.createUsedCoupons(usedCouponDto);

        return ResponseEntity.status(HttpStatus.CREATED)
                             .contentType(MediaType.APPLICATION_JSON)
                             .body(ShopResult.success());
    }

    /**
     * 삭제할 사용 쿠폰의 DTO 를 받아서 해당 사용 쿠폰을 삭제하는 DeleteMapping 을 지원합니다.
     *
     * @param usedCouponDto - 삭제할 사용 쿠폰의 정보를 담은 DTO 입니다.
     * @return Mapping URI 를 담은 응답 객체를 반환합니다.
     * @since 1.0.0
     */
    @Operation(summary = "사용 쿠폰 삭제",
        description = "쿠폰 사용이 취소되면 사용 쿠폰이 삭제됩니다.",
        parameters = @Parameter(name = "usedCouponDto",description = "삭제할 사용 쿠폰 요청 객체", required = true),
        responses = @ApiResponse(responseCode = "204",
            content = @Content(mediaType = "application/json",
                schema = @Schema(implementation = ShopResult.class)),
            useReturnTypeSchema = true))
    @DeleteMapping
    public ResponseEntity<ShopResult<Void>> deleteUsedCoupons(@Valid @RequestBody final UsedCouponDto usedCouponDto) {
        usedCouponService.deleteUsedCoupons(usedCouponDto);

        return ResponseEntity.status(HttpStatus.NO_CONTENT)
                             .contentType(MediaType.APPLICATION_JSON)
                             .body(ShopResult.success());
    }

}
