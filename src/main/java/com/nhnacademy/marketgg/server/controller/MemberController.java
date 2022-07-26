package com.nhnacademy.marketgg.server.controller;

import static org.springframework.http.HttpStatus.OK;

import com.nhnacademy.marketgg.server.annotation.Role;
import com.nhnacademy.marketgg.server.annotation.RoleCheck;
import com.nhnacademy.marketgg.server.dto.request.PointHistoryRequest;
import com.nhnacademy.marketgg.server.dto.request.ShopMemberSignUpRequest;
import com.nhnacademy.marketgg.server.dto.response.MemberResponse;
import com.nhnacademy.marketgg.server.dto.response.ShopMemberSignUpResponse;
import com.nhnacademy.marketgg.server.dto.response.common.CommonResponse;
import com.nhnacademy.marketgg.server.dto.response.common.SingleResponse;
import com.nhnacademy.marketgg.server.service.MemberService;
import com.nhnacademy.marketgg.server.service.PointService;
import java.net.URI;
import java.time.LocalDateTime;
import java.util.Objects;
import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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
@RequestMapping("/shop/v1/members")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;
    private final PointService pointService;

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
                             .location(URI.create("/shop/v1/members/" + memberId + "/ggpass"))
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
                             .location(URI.create("/shop/v1/members/" + memberId + "/ggpass/subscribe"))
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

        return ResponseEntity.status(OK)
                             .location(URI.create("/shop/v1/members/" + memberId + "/ggpass/withdraw"))

                             .contentType(MediaType.APPLICATION_JSON)
                             .build();
    }

    /**
     * 사용자 정보를 반환합니다.
     *
     * @param request - 요청 정보
     * @return - 사용자 정보를 반환합니다.
     */
    @RoleCheck(accessLevel = Role.ROLE_USER)
    @GetMapping
    public ResponseEntity<? extends CommonResponse> retrieveMember(HttpServletRequest request) {
        String uuid = request.getHeader("AUTH-ID");
        MemberResponse memberResponse = memberService.retrieveMember(uuid);

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

    public ResponseEntity<Void> doSignUp(@RequestBody final ShopMemberSignUpRequest shopMemberSignUpRequest) {
        ShopMemberSignUpResponse signUp = memberService.signUp(shopMemberSignUpRequest);

        if (Objects.nonNull(signUp.getReferrerMemberId())) {
            pointService.createPointHistory(signUp.getReferrerMemberId(),
                new PointHistoryRequest(5000, "추천인 이벤트"));
        }

        pointService.createPointHistory(signUp.getSignUpMemberId(),
            new PointHistoryRequest(5000, "회원 가입 추천인 이벤트"));

        return ResponseEntity.status(OK)
                             .location(URI.create("/shop/v1/members/signup"))
                             .contentType(MediaType.APPLICATION_JSON)
                             .build();
    }

}
