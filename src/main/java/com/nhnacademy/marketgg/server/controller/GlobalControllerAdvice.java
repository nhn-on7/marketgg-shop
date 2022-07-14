package com.nhnacademy.marketgg.server.controller;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * RestController 에서 나는 에러처리를 담당하는 ControllerAdvice 입니다.
 *
 * @version 1.0.0
 */
@RestControllerAdvice
public class GlobalControllerAdvice {

    /**
     * 전체 Exception 에 대해 관리하는 Handler 입니다.
     *
     * @param ex 에러 정보를 담은 Exception 입니다.
     * @return 해당 에러의 에러메세지를 반환합니다.
     * @since 1.0.0
     */
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public String handleException(Exception ex) {

        return "Error:" + ex.getMessage();
    }

}
