package com.nhnacademy.marketgg.server.controller;

import com.nhnacademy.marketgg.server.service.MemberService;
import java.net.URI;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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

    /**
     * 회원 서비스입니다.
     *
     * @since 1.0.0
     */
    private final MemberService memberService;

    /**
     * 선택한 회원의 GG 패스 갱신일시와 현재일시를 비교하는 Mapping 을 지원합니다.
     *
     * @param memberId  - GG 패스 갱신일자를 확인 할 회원의 식별번호입니다.
     * @return 선택한 회원의 GG 패스 갱신일이 현재시간보다 이후 시간이면 TRUE 를 반환, 아니면 False 를 반환합니다.
     * @since 1.0.0
     */
    @GetMapping("/{memberId}/ggpass")
    public ResponseEntity<Boolean> checkPassUpdatedAt(@PathVariable final Long memberId) {
        Boolean check = memberService.checkPassUpdatedAt(memberId);

        return ResponseEntity.status(HttpStatus.OK)
                .location(URI.create("/shop/v1/members/ggpass/" + memberId))
                .contentType(MediaType.APPLICATION_JSON)
                .body(check);
    }

    /**
     * 선택한 회원을 GG 패스에 구독시키는 Mapping 을 지원합니다.
     *
     * @param memberId - GG 패스를 구독할 회원의 식별번호입니다.
     * @return Mapping Uri 를 담은 응답 객체를 반환합니다.
     * @since 1.0.0
     */
    @PostMapping("/{memberId}/ggpass/subscribe")
    public ResponseEntity<Void> subscribePass(@PathVariable final Long memberId) {
        memberService.subscribePass(memberId);

        return ResponseEntity.status(HttpStatus.OK)
                .location(URI.create("/shop/v1/members/" + memberId + "/ggpass/subscribe" ))
                .contentType(MediaType.APPLICATION_JSON)
                .build();
    }

    /**
     * 선택한 회원을 GG 패스에 구독해지시키는 Mapping 을 지원합니다.
     *
     * @param memberId - GG 패스를 구독해지할 회원의 식별번호입니다.
     * @return Mapping Uri 를 담은 응답 객체를 반환합니다.
     * @since 1.0.0
     */
    @PostMapping("/{memberId}/ggpass/withdraw")
    public ResponseEntity<Void> withdrawPass(@PathVariable final Long memberId) {
        memberService.withdrawPass(memberId);

        return ResponseEntity.status(HttpStatus.OK)
                .location(URI.create("/shop/v1/members/" +  memberId + "/ggpass/withdraw"))
                .contentType(MediaType.APPLICATION_JSON)
                .build();
    }

}
