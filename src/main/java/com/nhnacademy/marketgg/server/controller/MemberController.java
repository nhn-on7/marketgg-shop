package com.nhnacademy.marketgg.server.controller;

import com.nhnacademy.marketgg.server.dto.request.GivenCouponRequest;
import com.nhnacademy.marketgg.server.dto.response.GivenCouponResponse;
import com.nhnacademy.marketgg.server.service.GivenCouponService;
import com.nhnacademy.marketgg.server.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 회원관리에 관련된 RestController 입니다.
 *
 * @version 1.0.0
 */
@RestController
@RequestMapping("/shop/v1/members")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;
    private final GivenCouponService givenCouponService;

    /**
     * 선택한 회원의 GG 패스 갱신일시를 반환하는 Mapping 을 지원합니다.
     *
     * @param memberId - GG 패스 갱신일시를 확인 할 회원의 식별번호입니다.
     * @return 선택한 회원의 GG 패스 갱신일을 반환합니다.
     * @since 1.0.0
     */
    @GetMapping("/{memberId}/ggpass")
    public ResponseEntity<LocalDateTime> retrievePassUpdatedAt(@PathVariable final Long memberId) {
        LocalDateTime check = memberService.retrievePassUpdatedAt(memberId);

        return ResponseEntity.status(HttpStatus.OK)
                             .location(URI.create("/shop/v1/members/" + memberId + "/ggpass"))
                             .contentType(MediaType.APPLICATION_JSON)
                             .body(check);
    }

    /**
     * 선택한 회원을 GG 패스에 구독시키는 Mapping 을 지원합니다.
     *
     * @param memberId - GG 패스를 구독할 회원의 식별번호입니다.
     * @return Mapping URI 를 담은 응답 객체를 반환합니다.
     * @since 1.0.0
     */
    @PostMapping("/{memberId}/ggpass/subscribe")
    public ResponseEntity<Void> subscribePass(@PathVariable final Long memberId) {
        memberService.subscribePass(memberId);

        return ResponseEntity.status(HttpStatus.OK)
                             .location(URI.create("/shop/v1/members/" + memberId + "/ggpass/subscribe"))
                             .contentType(MediaType.APPLICATION_JSON)
                             .build();
    }

    /**
     * 선택한 회원을 GG 패스에 구독해지시키는 Mapping 을 지원합니다.
     *
     * @param memberId - GG 패스를 구독해지할 회원의 식별번호입니다.
     * @return Mapping URI 를 담은 응답 객체를 반환합니다.
     * @since 1.0.0
     */
    @PostMapping("/{memberId}/ggpass/withdraw")
    public ResponseEntity<Void> withdrawPass(@PathVariable final Long memberId) {
        memberService.withdrawPass(memberId);

        return ResponseEntity.status(HttpStatus.OK)
                             .location(URI.create("/shop/v1/members/" + memberId + "/ggpass/withdraw"))
                             .contentType(MediaType.APPLICATION_JSON)
                             .build();
    }

    /**
     * 선택한 회원에게 쿠폰을 지급하는 PostMapping 을 지원합니다.
     *
     * @param memberId           - 쿠폰을 등록할 회원의 식별번호입니다.
     * @param givenCouponRequest - 등록할 쿠폰 번호 정보를 가진 요청 객체입니다.
     * @return Mapping URI 를 담은 응답 객체를 반환합니다.
     * @since 1.0.0
     */
    @PostMapping("/{memberId}/coupons")
    public ResponseEntity<Void> createGivenCoupons(@PathVariable final Long memberId,
                                                   @RequestBody final GivenCouponRequest givenCouponRequest) {

        givenCouponService.createGivenCoupons(memberId, givenCouponRequest);

        return ResponseEntity.status(HttpStatus.CREATED)
                             .location(URI.create("/shop/v1/members/" + memberId + "/coupons"))
                             .contentType(MediaType.APPLICATION_JSON)
                             .build();
    }

    /**
     * 선택한 회원에게 지급된 쿠폰 목록을 조회하는 GetMapping 을 지원합니다.
     *
     * @param memberId - 쿠폰을 조회할 회원의 식별번호입니다.
     * @return 회원에게 지급된 쿠폰 목록을 가진 DTO 객체를 반환합니다.
     * @since 1.0.0
     */
    @GetMapping("/{memberId}/coupons")
    public ResponseEntity<List<GivenCouponResponse>> retrieveGivenCoupons(@PathVariable final Long memberId,
                                                                          Pageable pageable) {
        List<GivenCouponResponse> givenCouponResponses = givenCouponService.retrieveGivenCoupons(memberId, pageable);

        return ResponseEntity.status(HttpStatus.OK)
                             .location(URI.create("/shop/v1/members/" + memberId + "/coupons"))
                             .contentType(MediaType.APPLICATION_JSON)
                             .body(givenCouponResponses);
    }

}
