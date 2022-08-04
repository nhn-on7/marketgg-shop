package com.nhnacademy.marketgg.server.controller;

import static org.springframework.http.HttpStatus.OK;

import com.nhnacademy.marketgg.server.annotation.Role;
import com.nhnacademy.marketgg.server.annotation.RoleCheck;
import com.nhnacademy.marketgg.server.annotation.UUID;
import com.nhnacademy.marketgg.server.dto.AuthInfo;
import com.nhnacademy.marketgg.server.dto.MemberInfo;
import com.nhnacademy.marketgg.server.dto.request.coupon.GivenCouponCreateRequest;
import com.nhnacademy.marketgg.server.dto.request.member.MemberWithdrawRequest;
import com.nhnacademy.marketgg.server.dto.request.member.ShopMemberSignUpRequest;
import com.nhnacademy.marketgg.server.dto.response.GivenCouponResponse;
import com.nhnacademy.marketgg.server.dto.response.common.CommonResponse;
import com.nhnacademy.marketgg.server.dto.response.common.ListResponse;
import com.nhnacademy.marketgg.server.dto.response.common.SingleResponse;
import com.nhnacademy.marketgg.server.dto.response.member.MemberResponse;
import com.nhnacademy.marketgg.server.service.GivenCouponService;
import com.nhnacademy.marketgg.server.service.MemberService;
import com.nhnacademy.marketgg.server.service.PointService;
import java.net.URI;
import java.time.LocalDateTime;
import java.util.List;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 회원관리에 관련된 RestController 입니다.
 *
 * @version 1.0.0
 */
@RestController
@RequestMapping("/members")
@RequiredArgsConstructor
public class MemberController {

    private static final String MEMBER_PREFIX = "/member/";

    private final MemberService memberService;
    private final PointService pointService;
    private final GivenCouponService givenCouponService;

    /**
     * 선택한 회원의 GG 패스 갱신일시를 반환하는 GET Mapping 을 지원합니다.
     *
     * @param memberId - GG 패스 갱신일시를 확인 할 회원의 식별번호입니다.
     * @return 선택한 회원의 GG 패스 갱신일을 반환합니다.
     * @since 1.0.0
     */
    @GetMapping("/{memberId}/ggpass")
    public ResponseEntity<LocalDateTime> retrievePassUpdatedAt(@PathVariable final Long memberId) {
        LocalDateTime check = memberService.retrievePassUpdatedAt(memberId);

        return ResponseEntity.status(OK)
                             .location(URI.create(MEMBER_PREFIX + memberId + "/ggpass"))
                             .contentType(MediaType.APPLICATION_JSON)
                             .body(check);
    }

    /**
     * 선택한 회원을 GG 패스에 구독시키는 POST Mapping 을 지원합니다.
     *
     * @param memberId - GG 패스를 구독할 회원의 식별번호입니다.
     * @return Mapping URI 를 담은 응답 객체를 반환합니다.
     * @since 1.0.0
     */
    @PostMapping("/{memberId}/ggpass/subscribe")
    public ResponseEntity<Void> subscribePass(@PathVariable final Long memberId) {
        memberService.subscribePass(memberId);

        return ResponseEntity.status(OK)
                             .location(URI.create(MEMBER_PREFIX + memberId + "/ggpass/subscribe"))
                             .contentType(MediaType.APPLICATION_JSON)
                             .build();
    }

    /**
     * 선택한 회원을 GG 패스에 구독해지시키는 POST Mapping 을 지원합니다.
     *
     * @param memberId - GG 패스를 구독해지할 회원의 식별번호입니다.
     * @return Mapping URI 를 담은 응답 객체를 반환합니다.
     * @since 1.0.0
     */
    @PostMapping("/{memberId}/ggpass/withdraw")
    public ResponseEntity<Void> withdrawPass(@PathVariable final Long memberId) {
        memberService.withdrawPass(memberId);

        return ResponseEntity.status(OK).location(URI.create(MEMBER_PREFIX + memberId + "/ggpass/withdraw"))
                             .contentType(MediaType.APPLICATION_JSON).build();
    }

    /**
     * 사용자 정보를 반환합니다.
     *
     * @param authInfo   - Auth Server 의 사용자 정보
     * @param memberInfo - Shop Server 의 사용자 정보
     * @return - 사용자 정보를 반환합니다.
     */
    @RoleCheck(accessLevel = Role.ROLE_USER)
    @GetMapping
    public ResponseEntity<CommonResponse> retrieveMember(final AuthInfo authInfo, final MemberInfo memberInfo) {
        MemberResponse memberResponse = new MemberResponse(authInfo, memberInfo);

        return ResponseEntity.status(HttpStatus.OK)
                             .contentType(MediaType.APPLICATION_JSON)
                             .body(new SingleResponse<>(memberResponse));
    }

    /**
     * Client 에서 받은 회원가입 Form 에서 입력한 정보로 회원가입을 하는 로직입니다.
     * 회원가입시 추천인을 입력했고, 해당 회원이 존재하면 추천인과 추천인을 입력한 회원은 적립금을 받습니다.
     *
     * @param shopMemberSignUpRequest - shop
     * @return Mapping URI 를 담은 응답 객체를 반환합니다.
     * @since 1.0.0
     */
    @PostMapping("/signup")
    public ResponseEntity<CommonResponse> doSignUp(@RequestBody final ShopMemberSignUpRequest shopMemberSignUpRequest) {
        memberService.signUp(shopMemberSignUpRequest);

        return ResponseEntity.status(HttpStatus.CREATED)
                             .contentType(MediaType.APPLICATION_JSON)
                             .body(new SingleResponse<>("Add success"));
    }

    /**
     * Shop-Server 회원 Soft 삭제를 위한 메소드 입니다.
     *
     * @param uuid                  - 회원탈퇴를 신청한 회원입니다.
     * @param memberWithdrawRequest - 회원탈퇴 시점을 가지고 있는 객체 입니다.
     * @return - 상태코드를 반환합니다.
     */
    @DeleteMapping
    public ResponseEntity<Void> withdraw(@UUID String uuid, final MemberWithdrawRequest memberWithdrawRequest) {
        memberService.withdraw(uuid, memberWithdrawRequest);

        return ResponseEntity.status(OK)
                             .location(URI.create("/members"))
                             .contentType(MediaType.APPLICATION_JSON)
                             .build();
    }

    /**
     * 선택한 회원에게 쿠폰을 지급하는 PostMapping 을 지원합니다.
     *
     * @param memberInfo         - 쿠폰을 등록할 회원의 정보입니다.
     * @param givenCouponRequest - 등록할 쿠폰 번호 정보를 가진 요청 객체입니다.
     * @return Mapping URI 를 담은 응답 객체를 반환합니다.
     * @since 1.0.0
     */
    @RoleCheck(accessLevel = Role.ROLE_USER)
    @PostMapping("/coupons")
    public ResponseEntity<CommonResponse> createGivenCoupons(final MemberInfo memberInfo,
                                                             @Valid @RequestBody final GivenCouponCreateRequest givenCouponRequest) {

        givenCouponService.createGivenCoupons(memberInfo, givenCouponRequest);

        return ResponseEntity.status(HttpStatus.CREATED)
                             .contentType(MediaType.APPLICATION_JSON)
                             .body(new SingleResponse<>("Add success"));
    }

    /**
     * 선택한 회원에게 지급된 쿠폰 목록을 조회하는 GetMapping 을 지원합니다.
     *
     * @param memberInfo - 쿠폰을 등록할 회원의 정보입니다.
     * @return 회원에게 지급된 쿠폰 목록을 가진 DTO 객체를 반환합니다.
     * @since 1.0.0
     */
    @RoleCheck(accessLevel = Role.ROLE_USER)
    @GetMapping("/coupons")
    public ResponseEntity<CommonResponse> retrieveGivenCoupons(final MemberInfo memberInfo, final Pageable pageable) {
        List<GivenCouponResponse> givenCouponResponses = givenCouponService.retrieveGivenCoupons(memberInfo, pageable);

        return ResponseEntity.status(HttpStatus.OK)
                             .contentType(MediaType.APPLICATION_JSON)
                             .body(new ListResponse<>(givenCouponResponses));
    }

}
