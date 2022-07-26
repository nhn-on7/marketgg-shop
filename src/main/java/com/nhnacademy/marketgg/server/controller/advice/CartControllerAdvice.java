package com.nhnacademy.marketgg.server.controller.advice;

import static org.springframework.http.HttpStatus.*;
import static org.springframework.http.MediaType.*;

import com.nhnacademy.marketgg.server.dto.response.common.ErrorEntity;
import com.nhnacademy.marketgg.server.exception.cart.CartNotFoundException;
import com.nhnacademy.marketgg.server.exception.member.MemberNotFoundException;
import com.nhnacademy.marketgg.server.exception.product.ProductNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@Order(10000)
@RestControllerAdvice
public class CartControllerAdvice {

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
