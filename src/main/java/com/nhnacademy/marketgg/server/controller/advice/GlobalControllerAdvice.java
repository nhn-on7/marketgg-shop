package com.nhnacademy.marketgg.server.controller.advice;

import com.nhnacademy.marketgg.server.dto.ErrorEntity;
import com.nhnacademy.marketgg.server.exception.NotFoundException;
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
     * 자원을 찾지 못했을 때 발생하는 예외를 처리합니다.
     *
     * @param e - 발생한 예외
     * @return - 404 Not Found
     */
    @ExceptionHandler({
        NotFoundException.class
    })
    public ResponseEntity<ErrorEntity> notFoundExceptionHandle(final NotFoundException e) {
        log.error(e.toString());
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                             .contentType(MediaType.APPLICATION_JSON)
                             .body(new ErrorEntity(e.getMessage()));
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

        try {
            if (bindingResult.hasErrors()) {
                builder.append("Reason: ")
                       .append(Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage())
                       .append(System.lineSeparator())
                       .append("At: ")
                       .append(bindingResult.getObjectName())
                       .append(System.lineSeparator())
                       .append("Field: ")
                       .append(Objects.requireNonNull(bindingResult.getFieldError()).getField())
                       .append(System.lineSeparator())
                       .append("Not valid input: ")
                       .append(Objects.requireNonNull(bindingResult.getFieldError()).getRejectedValue())
                       .append(System.lineSeparator());
            }
        } catch (NullPointerException e) {
            log.info("Field Error Null");
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                             .contentType(MediaType.APPLICATION_JSON)
                             .body(new ErrorEntity(builder.toString()));
    }

    /**
     * 전체 Exception 에 대해 관리하는 Handler 입니다.
     *
     * @param ex - 에러 정보를 담은 Exception 입니다.
     * @return 해당 에러의 에러메세지를 반환합니다.
     * @since 1.0.0
     */
    @Order
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorEntity> handleException(final Exception ex) {
        log.error("", ex);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                             .contentType(MediaType.APPLICATION_JSON)
                             .body(new ErrorEntity(ex.getMessage()));
    }

}
