package com.nhnacademy.marketgg.server.controller.advice;

import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.MediaType.APPLICATION_JSON;

import com.nhnacademy.marketgg.server.dto.response.common.ErrorEntity;
import com.nhnacademy.marketgg.server.exception.cart.CartNotFoundException;
import com.nhnacademy.marketgg.server.exception.member.MemberNotFoundException;
import com.nhnacademy.marketgg.server.exception.product.ProductNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 장바구니 로직을 처리할 때 발생하는 예외를 처리합니다.
 *
 * @version 1.0.0
 */
@Slf4j
@Order(10000)
@RestControllerAdvice
public class CartControllerAdvice {

    /**
     * 장바구니 로직을 처리할 때 발생하는 예외를 다룹니다.
     *
     * @param e - 발생한 예외
     * @return - 404 Not Found
     */
    @ExceptionHandler({
        MemberNotFoundException.class,
        ProductNotFoundException.class,
        CartNotFoundException.class
    })
    public ResponseEntity<ErrorEntity> cartExceptionHandler(Exception e) {
        log.error("", e);
        return ResponseEntity.status(NOT_FOUND)
                             .contentType(APPLICATION_JSON)
                             .body(new ErrorEntity(e.getMessage()));
    }

}
