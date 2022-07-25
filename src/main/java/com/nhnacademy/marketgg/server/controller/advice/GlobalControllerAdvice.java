package com.nhnacademy.marketgg.server.controller.advice;

import com.nhnacademy.marketgg.server.dto.response.common.ErrorEntity;
import java.util.Objects;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * RestController 에서 나는 에러처리를 담당하는 ControllerAdvice 입니다.
 *
 * @version 1.0.0
 */
@Slf4j
@Order
@RestControllerAdvice
public class GlobalControllerAdvice {

    /**
     * 전체 Exception 에 대해 관리하는 Handler 입니다.
     *
     * @param ex - 에러 정보를 담은 Exception 입니다.
     * @return 해당 에러의 에러메세지를 반환합니다.
     * @since 1.0.0
     */
    @ExceptionHandler(Exception.class)
    private ResponseEntity<ErrorEntity> handleException(final Exception ex) {
        log.error("", ex);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                             .contentType(MediaType.APPLICATION_JSON)
                             .body(new ErrorEntity(ex.getMessage()));
    }

    /**
     * 유효성 검사 Exception 에 대해 관리하는 Handler 입니다.
     *
     * @param ex - 에러 정보를 담은 Exception 입니다.
     * @return 해당 에러의 에러메세지를 반환합니다.
     * @since 1.0.0
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    private ResponseEntity<ErrorEntity> handleNotValidException(
        final MethodArgumentNotValidException ex) {

        log.error("", ex);
        BindingResult bindingResult = ex.getBindingResult();

        StringBuilder builder = new StringBuilder();
        builder.append("[Valid Error]\n");

        if (bindingResult.hasErrors()) {
            builder.append("Reason: ")
                   .append(bindingResult.getFieldError().getDefaultMessage())
                   .append("\n")
                   .append("At: ")
                   .append(bindingResult.getObjectName())
                   .append("\n")
                   .append("Field: ")
                   .append(bindingResult.getFieldError().getField())
                   .append("\n")
                   .append("Not valid input: ")
                   .append(bindingResult.getFieldError().getRejectedValue())
                   .append("\n");
        }

        return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED)
                             .contentType(MediaType.APPLICATION_JSON)
                             .body(new ErrorEntity(builder.toString()));
    }

}
