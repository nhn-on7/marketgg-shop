package com.nhnacademy.marketgg.server.controller.advice;

import com.nhnacademy.marketgg.server.dto.response.common.ErrorEntity;
import com.nhnacademy.marketgg.server.exception.auth.UnAuthenticException;
import com.nhnacademy.marketgg.server.exception.member.MemberNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 사용자 관련 예외를 처리하는 클래스입니다.
 *
 * @version 1.0.0
 */
@Slf4j
@Order(10000)
@RestControllerAdvice
public class MemberControllerAdvice {


    @ExceptionHandler({
        MemberNotFoundException.class
    })
    public ResponseEntity<ErrorEntity> memberExceptionHandler(MemberNotFoundException e) {
        log.error(e.toString());
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                             .contentType(MediaType.APPLICATION_JSON)
                             .body(new ErrorEntity(e.getMessage()));
    }

    @ExceptionHandler({
        UnAuthenticException.class
    })
    public ResponseEntity<ErrorEntity> memberExceptionHandler(Exception e) {
        log.error(e.toString());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED )
                             .contentType(MediaType.APPLICATION_JSON)
                             .body(new ErrorEntity(e.getMessage()));
    }

}
