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

@RestController
@RequestMapping("/shop/v1/members")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/ggpass/{memberId}")
    public ResponseEntity<Boolean> checkPassUpdatedAt(@PathVariable final Long memberId) {
        Boolean check = memberService.checkPassUpdatedAt(memberId);

        return ResponseEntity.status(HttpStatus.OK)
                .location(URI.create("/shop/v1/members/ggpass/" + memberId))
                .contentType(MediaType.APPLICATION_JSON)
                .body(check);
    }

    @PostMapping("/ggpass/join/{memberId}")
    public ResponseEntity<Void> joinPass(@PathVariable final Long memberId) {
        memberService.joinPass(memberId);

        return ResponseEntity.status(HttpStatus.OK)
                .location(URI.create("/shop/v1/members/ggpass/join/" + memberId))
                .contentType(MediaType.APPLICATION_JSON)
                .build();
    }

    @PostMapping("/ggpass/withdraw/{memberId}")
    public ResponseEntity<Void> withdrawPass(@PathVariable final Long memberId) {
        memberService.withdrawPass(memberId);

        return ResponseEntity.status(HttpStatus.OK)
                .location(URI.create("/shop/v1/members/ggpass/withdraw/" + memberId))
                .contentType(MediaType.APPLICATION_JSON)
                .build();
    }

}
