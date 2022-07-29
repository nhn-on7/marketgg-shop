package com.nhnacademy.marketgg.server.controller.advice;

import com.nhnacademy.marketgg.server.exception.auth.UnAuthenticException;
import com.nhnacademy.marketgg.server.exception.auth.UnAuthorizationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class AuthControllerAdvice {

    @ExceptionHandler(UnAuthenticException.class)
    public ResponseEntity<Void> handleUnAuthentic(UnAuthenticException e) {
        log.error("로그인 필요", e);
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                             .build();
    }

    @ExceptionHandler(UnAuthorizationException.class)
    public ResponseEntity<Void> handleUnAuthorization(UnAuthorizationException e) {
        log.error("권한 부족", e);
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                             .build();
    }

}
