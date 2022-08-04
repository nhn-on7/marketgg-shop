package com.nhnacademy.marketgg.server.controller;

import com.nhnacademy.marketgg.server.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

/**
 * 주문에 관련된 Rest Controller 입니다.
 *
 * @version 1.0.0
 * @author 김정민
 */
@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;
    private static final String ORDER_PREFIX = "/orders/";

    // 주문서 작성
    @PostMapping
    public ResponseEntity<Void> createOrder() {

        return ResponseEntity.status(HttpStatus.CREATED)
                             .location(URI.create(ORDER_PREFIX))
                             .contentType(MediaType.APPLICATION_JSON)
                             .build();
    }

    // 주문 목록 조회 - 관리자는 모든 회원, 회원은 본인 것
    @GetMapping
    public ResponseEntity<Void> retrieveOrderList() {

        return ResponseEntity.status(HttpStatus.OK)
                             .location(URI.create(ORDER_PREFIX))
                             .contentType(MediaType.APPLICATION_JSON)
                             .build();
    }

    // 주문 상세 조회
    @GetMapping("/{orderId}")
    public ResponseEntity<Void> retrieveOrderDetail() {

        return ResponseEntity.status(HttpStatus.OK)
                             .location(URI.create(ORDER_PREFIX))
                             .contentType(MediaType.APPLICATION_JSON)
                             .build();
    }

    // 운송장 번호 생성된 후 주문서 수정
    @PatchMapping("/{orderId}")
    public ResponseEntity<Void> updateOrder() {

        return ResponseEntity.status(HttpStatus.OK)
                             .location(URI.create(ORDER_PREFIX))
                             .contentType(MediaType.APPLICATION_JSON)
                             .build();
    }

    // 주문 취소 -> 주문서 삭제
    @DeleteMapping("/{orderId}")
    public ResponseEntity<Void> deleteOrder() {

        return ResponseEntity.status(HttpStatus.OK)
                             .location(URI.create(ORDER_PREFIX))
                             .contentType(MediaType.APPLICATION_JSON)
                             .build();
    }

}
