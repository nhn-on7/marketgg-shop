package com.nhnacademy.marketgg.server.controller.admin;

import com.nhnacademy.marketgg.server.annotation.Role;
import com.nhnacademy.marketgg.server.annotation.RoleCheck;
import com.nhnacademy.marketgg.server.dto.request.coupon.CouponDto;
import com.nhnacademy.marketgg.server.dto.response.common.CommonResponse;
import com.nhnacademy.marketgg.server.dto.response.common.ListResponse;
import com.nhnacademy.marketgg.server.dto.response.common.SingleResponse;
import com.nhnacademy.marketgg.server.service.CouponService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
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

import javax.validation.Valid;
import java.util.List;

/**
 * 쿠폰 관련 Rest Controller 입니다.
 *
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
     * @since 1.0.0
     */
    @RoleCheck(accessLevel = Role.ROLE_ADMIN)
    @PostMapping
    public ResponseEntity<CommonResponse> createCoupon(@Valid @RequestBody final CouponDto couponDto) {
        couponService.createCoupon(couponDto);

        return ResponseEntity.status(HttpStatus.CREATED)
                             .contentType(MediaType.APPLICATION_JSON)
                             .body(new SingleResponse<>("Add success"));
    }

    /**
     * couponId 에 해당하는 쿠폰을 조회하는 GetMapping 을 지원합니다.
     *
     * @param couponId - 조회할 쿠폰의 식별번호 입니다.
     * @return 조회한 쿠폰의 정보를 담은 객체를 반환합니다.
     * @since 1.0.0
     */
    @RoleCheck(accessLevel = Role.ROLE_ADMIN)
    @GetMapping("/{couponId}")
    public ResponseEntity<CommonResponse> retrieveCoupon(@PathVariable final Long couponId) {
        CouponDto couponResponse = couponService.retrieveCoupon(couponId);

        return ResponseEntity.status(HttpStatus.OK)
                             .body(new SingleResponse<>(couponResponse));
    }

    /**
     * 전체 쿠폰 목록을 조회하는 GetMapping 을 지원합니다.
     *
     * @return 전체 쿠폰 목록 DTO 를 List 로 반환합니다.
     * @since 1.0.0
     */
    @RoleCheck(accessLevel = Role.ROLE_ADMIN)
    @GetMapping
    public ResponseEntity<CommonResponse> retrieveCoupons(final Pageable pageable) {
        List<CouponDto> couponResponses = couponService.retrieveCoupons(pageable);

        return ResponseEntity.status(HttpStatus.OK)
                             .body(new ListResponse<>(couponResponses));
    }

    /**
     * 선택한 쿠폰을 수정하는 PutMapping 을 지원합니다.
     *
     * @param couponId  - 수정할 쿠폰의 식별번호입니다.
     * @param couponDto - 수정할 정보를 담은 DTO 입니다.
     * @return Mapping URI 를 담은 응답 객체를 반환합니다.
     * @since 1.0.0
     */
    @RoleCheck(accessLevel = Role.ROLE_ADMIN)
    @PutMapping("/{couponId}")
    public ResponseEntity<CommonResponse> updateCoupon(@PathVariable final Long couponId,
                                                       @Valid @RequestBody final CouponDto couponDto) {

        couponService.updateCoupon(couponId, couponDto);

        return ResponseEntity.status(HttpStatus.OK)
                             .contentType(MediaType.APPLICATION_JSON)
                             .body(new SingleResponse<>("Update success"));
    }

    /**
     * 선택한 쿠폰을 삭제하는 DeleteMapping 을 지원합니다.
     *
     * @param couponId - 삭제할 쿠폰의 식별번호입니다.
     * @return Mapping URI 를 담은 응답 객체를 반환합니다.
     * @since 1.0.0
     */
    @RoleCheck(accessLevel = Role.ROLE_ADMIN)
    @DeleteMapping("/{couponId}")
    public ResponseEntity<CommonResponse> deleteCoupon(@PathVariable final Long couponId) {
        couponService.deleteCoupon(couponId);

        return ResponseEntity.status(HttpStatus.NO_CONTENT)
                             .contentType(MediaType.APPLICATION_JSON)
                             .body(new SingleResponse<>("Delete success"));
    }

}
