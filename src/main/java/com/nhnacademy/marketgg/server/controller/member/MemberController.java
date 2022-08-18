package com.nhnacademy.marketgg.server.controller.member;

import static org.springframework.http.HttpStatus.OK;

import com.nhnacademy.marketgg.server.annotation.Auth;
import com.nhnacademy.marketgg.server.annotation.UUID;
import com.nhnacademy.marketgg.server.dto.ShopResult;
import com.nhnacademy.marketgg.server.dto.info.AuthInfo;
import com.nhnacademy.marketgg.server.dto.info.MemberInfo;
import com.nhnacademy.marketgg.server.dto.request.coupon.GivenCouponCreateRequest;
import com.nhnacademy.marketgg.server.dto.request.member.MemberWithdrawRequest;
import com.nhnacademy.marketgg.server.dto.request.member.ShopMemberSignUpRequest;
import com.nhnacademy.marketgg.server.dto.response.common.CommonResponse;
import com.nhnacademy.marketgg.server.dto.response.common.ListResponse;
import com.nhnacademy.marketgg.server.dto.response.common.SingleResponse;
import com.nhnacademy.marketgg.server.dto.response.coupon.GivenCouponResponse;
import com.nhnacademy.marketgg.server.dto.response.member.MemberResponse;
import com.nhnacademy.marketgg.server.dto.response.product.ProductInquiryByMemberResponse;
import com.nhnacademy.marketgg.server.service.coupon.GivenCouponService;
import com.nhnacademy.marketgg.server.service.member.MemberService;
import com.nhnacademy.marketgg.server.service.product.ProductInquiryPostService;
import java.net.URI;
import java.time.LocalDateTime;
import java.util.List;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 회원관리에 관련된 RestController 입니다.
 *
 * @author 김훈민, 민아영, 박세완
 * @version 1.0.0
 */
@Slf4j
@Auth
@RestController
@RequestMapping("/members")
@RequiredArgsConstructor
public class MemberController {

    private static final String MEMBER_PREFIX = "/member/";

    private final MemberService memberService;
    private final GivenCouponService givenCouponService;
    private final ProductInquiryPostService productInquiryPostService;

    /**
     * 선택한 회원의 GG 패스 갱신일시를 반환하는 GET Mapping 을 지원합니다.
     *
     * @param memberInfo - 회원의 정보를 담은 객체입니다.
     * @return 선택한 회원의 GG 패스 갱신일을 반환합니다.
     * @since 1.0.0
     */
    @GetMapping("/ggpass")
    public ResponseEntity<ShopResult<LocalDateTime>> retrievePassUpdatedAt(final MemberInfo memberInfo) {

        LocalDateTime data = memberService.retrievePassUpdatedAt(memberInfo.getId());

        return ResponseEntity.status(OK)
                             .location(URI.create(MEMBER_PREFIX + "/ggpass"))
                             .contentType(MediaType.APPLICATION_JSON)
                             .body(ShopResult.success(data));
    }

    /**
     * 선택한 회원을 GG 패스에 구독시키는 POST Mapping 을 지원합니다.
     *
     * @param memberInfo - 회원의 정보를 담은 객체입니다.
     * @return Mapping URI 를 담은 응답 객체를 반환합니다.
     * @since 1.0.0
     */
    @PostMapping("/ggpass/subscribe")
    public ResponseEntity<ShopResult<Void>> subscribePass(final MemberInfo memberInfo) {
        memberService.subscribePass(memberInfo.getId());

        return ResponseEntity.status(OK)
                             .location(URI.create(MEMBER_PREFIX + "/ggpass/subscribe"))
                             .body(ShopResult.success());
    }

    /**
     * 선택한 회원을 GG 패스에 구독해지시키는 POST Mapping 을 지원합니다.
     *
     * @param memberInfo - 회원의 정보를 담은 객체입니다.
     * @return Mapping URI 를 담은 응답 객체를 반환합니다.
     * @since 1.0.0
     */
    @PostMapping("/ggpass/withdraw")
    public ResponseEntity<ShopResult<Void>> withdrawPass(final MemberInfo memberInfo) {
        memberService.withdrawPass(memberInfo.getId());

        return ResponseEntity.status(OK)
                             .location(URI.create(MEMBER_PREFIX + "/ggpass/withdraw"))
                             .body(ShopResult.success());
    }

    /**
     * 사용자 정보를 반환합니다.
     *
     * @param authInfo   - Auth Server 의 사용자 정보
     * @param memberInfo - Shop Server 의 사용자 정보
     * @return - 사용자 정보를 반환합니다.
     */
    @GetMapping
    public ResponseEntity<CommonResponse> retrieveMember(final AuthInfo authInfo, final MemberInfo memberInfo) {
        MemberResponse memberResponse = new MemberResponse(authInfo, memberInfo);

        log.info("MemberResponse = {}", memberResponse);

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
     * @author 민아영
     * @since 1.0.0
     */
    @PostMapping("/coupons")
    public ResponseEntity<CommonResponse> createGivenCoupons(final MemberInfo memberInfo,
                                                             @Valid @RequestBody final
                                                             GivenCouponCreateRequest givenCouponRequest) {

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
     * @author 민아영
     * @since 1.0.0
     */
    @GetMapping("/coupons")
    public ResponseEntity<CommonResponse> retrieveGivenCoupons(final MemberInfo memberInfo, final Pageable pageable) {
        List<GivenCouponResponse> givenCouponResponses = givenCouponService.retrieveGivenCoupons(memberInfo, pageable);

        return ResponseEntity.status(HttpStatus.OK)
                             .contentType(MediaType.APPLICATION_JSON)
                             .body(new ListResponse<>(givenCouponResponses));
    }

    /**
     * 한 회원이 상품에 대해 문의한 전체 상품 문의 글을 조회하는 GET Mapping 을 지원합니다.
     *
     * @param memberInfo - 상품 문의 글을 조회할 회원의 정보 입니다.
     * @return - List<ProductInquiryByMemberResponse> 를 담은 응답 객체를 반환 합니다.
     * @author 민아영
     * @since 1.0.0
     */
    @GetMapping("/product-inquiries")
    public ResponseEntity<CommonResponse> retrieveProductInquiry(final MemberInfo memberInfo,
                                                                 final Pageable pageable) {
        Page<ProductInquiryByMemberResponse> productInquiryResponses
            = productInquiryPostService.retrieveProductInquiryByMemberId(memberInfo, pageable);

        return ResponseEntity.status(HttpStatus.OK)
                             .contentType(MediaType.APPLICATION_JSON)
                             .body(new SingleResponse<>(productInquiryResponses));
    }

}
