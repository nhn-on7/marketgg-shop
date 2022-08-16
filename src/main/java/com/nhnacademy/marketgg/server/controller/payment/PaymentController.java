package com.nhnacademy.marketgg.server.controller.payment;

import com.nhnacademy.marketgg.server.dto.payment.request.PaymentCancelRequest;
import com.nhnacademy.marketgg.server.dto.payment.request.PaymentRequest;
import com.nhnacademy.marketgg.server.dto.payment.request.PaymentVerifyRequest;
import com.nhnacademy.marketgg.server.dto.response.common.CommonResponse;
import com.nhnacademy.marketgg.server.dto.response.common.SingleResponse;
import com.nhnacademy.marketgg.server.service.payment.PaymentService;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * 결제와 관련된 요청 및 응답을 제공합니다.
 *
 * @author 이제훈
 * @version 1.0
 * @since 1.0
 */
@Slf4j
@RestController
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    /**
     * 회원의 결제 요청에 대한 검증을 처리합니다.
     *
     * @param paymentRequest - 결제 요청 정보
     * @return 성공 여부 응답 결과 반환
     */
    @PostMapping("/payments/verify")
    public ResponseEntity<CommonResponse> verifyRequest(@RequestBody @Valid final PaymentVerifyRequest paymentRequest) {
        log.info("verifyRequest: {}", paymentRequest);

        paymentService.verifyRequest(paymentRequest);

        return ResponseEntity.status(HttpStatus.OK)
                             .contentType(MediaType.APPLICATION_JSON)
                             .body(new SingleResponse<>());
    }

    /**
     * 최종 결제를 요청합니다.
     *
     * @param paymentRequest - 결제 요청 정보
     * @return 결제대행사에서 결제 승인 처리로 인한 응답 정보 객체
     */
    @PostMapping("/payments/confirm")
    public ResponseEntity<CommonResponse> confirmPayment(@RequestBody @Valid final PaymentRequest paymentRequest) {
        log.info("confirmPayment: {}", paymentRequest);

        paymentService.pay(paymentRequest);

        return ResponseEntity.status(HttpStatus.CREATED)
                             .contentType(MediaType.APPLICATION_JSON)
                             .body(new SingleResponse<>());
    }

    /**
     * 승인된 결제에 대해 결제 취소 요청을 처리합니다.
     *
     * @param paymentRequest - 결제 요청 정보
     * @return 결제대행사에서 결제 승인 처리로 인한 응답 정보 객체
     */
    @PostMapping("/payments/{paymentKey}/cancel")
    public ResponseEntity<CommonResponse> cancelPayment(@PathVariable String paymentKey,
                                                        @RequestBody @Valid final PaymentCancelRequest paymentRequest) {
        log.info("cancelPayment: {}", paymentRequest);

        paymentService.cancelPayment(paymentKey, paymentRequest);

        return ResponseEntity.status(HttpStatus.CREATED)
                             .contentType(MediaType.APPLICATION_JSON)
                             .body(new SingleResponse<>());
    }

}
