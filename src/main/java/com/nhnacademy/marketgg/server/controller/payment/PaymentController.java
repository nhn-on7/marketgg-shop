package com.nhnacademy.marketgg.server.controller.payment;

import com.nhnacademy.marketgg.server.dto.ShopResult;
import com.nhnacademy.marketgg.server.dto.payment.request.PaymentCancelRequest;
import com.nhnacademy.marketgg.server.dto.payment.request.PaymentConfirmRequest;
import com.nhnacademy.marketgg.server.dto.payment.request.VirtualAccountCreateRequest;
import com.nhnacademy.marketgg.server.dto.payment.request.VirtualAccountDepositRequest;
import com.nhnacademy.marketgg.server.dto.payment.response.PaymentResponse;
import com.nhnacademy.marketgg.server.dto.response.order.OrderToPayment;
import com.nhnacademy.marketgg.server.service.payment.PaymentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.net.URI;
import java.util.function.BooleanSupplier;

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
     * 결제 요청에 대해 검증 수행 요청을 처리합니다.
     * 지연 평가(lazy evaluation) 방법을 통해 검증 수행 결과를 클라이언트에게 반환합니다.
     *
     * @param paymentVerifyRequest - 결제 검증 요청 데이터
     * @return 최종 결제 금액 일치 여부
     */
    @Operation(summary = "결제 검증 요청",
               description = "결제 요청에 대해 검증 수행 요청을 처리합니다.",
               parameters = @Parameter(description = "결제 검증 요청 데이터", required = true),
               responses = @ApiResponse(responseCode = "200",
                                        content = @Content(mediaType = "application/json",
                                                           schema = @Schema(implementation = ShopResult.class))))
    @PostMapping("/payments/verify")
    public ResponseEntity<ShopResult<Boolean>> verifyRequest(@RequestBody @Valid final
                                                             OrderToPayment paymentVerifyRequest) {

        log.info("verifyRequest: {}", paymentVerifyRequest);

        BooleanSupplier result = paymentService.verifyRequest(paymentVerifyRequest);

        return ResponseEntity.status(HttpStatus.OK)
                             .contentType(MediaType.APPLICATION_JSON)
                             .body(ShopResult.successWith(result.getAsBoolean()));
    }

    /**
     * 최종 결제를 요청합니다.
     *
     * @param paymentRequest - 결제 요청 데이터
     * @return 결제대행사에서 결제 승인 처리로 인한 응답 데이터 객체
     */
    @Operation(summary = "최종 결제 승인",
               description = "결제대행사에 결제 승인 처리 요청을 하고 자체 데이터베이스에 결제 데이터를 영속화합니다.",
               parameters = @Parameter(description = "결제 승인 요청 객체", required = true),
               responses = @ApiResponse(responseCode = "201",
                                        content = @Content(mediaType = "application/json",
                                                           schema = @Schema(implementation = ShopResult.class)),
                                        useReturnTypeSchema = true))
    @PostMapping("/payments/confirm")
    public ResponseEntity<ShopResult<PaymentResponse>> confirmPayment(@RequestBody @Valid final
                                                                      PaymentConfirmRequest paymentRequest) {

        log.info("confirmPayment: {}", paymentRequest);

        PaymentResponse data = paymentService.pay(paymentRequest);

        return ResponseEntity.status(HttpStatus.CREATED)
                             .location(URI.create("/"))
                             .contentType(MediaType.APPLICATION_JSON)
                             .body(ShopResult.successWith(data));
    }

    /**
     * 회원이 희망하는 은행의 가상계좌 발급 요청을 처리합니다.
     *
     * @param request - 가상계좌 발급 요청 데이터
     * @return 발급한 가상계좌 데이터를 포함한 결과 데이터
     */
    @PostMapping("/payments/virtual-accounts")
    public ResponseEntity<ShopResult<String>> createVirtualAccounts(@RequestBody @Valid final
                                                                    VirtualAccountCreateRequest request) {
        log.info("createVirtualAccounts: {}", request);

        paymentService.createVirtualAccounts(request);

        return ResponseEntity.status(HttpStatus.CREATED)
                             .contentType(MediaType.APPLICATION_JSON)
                             .body(ShopResult.successWithDefaultMessage());
    }

    /**
     * 회원이 주문한 건에 대해 가상계좌에 입금하게 되면 받는 알림을 처리합니다.
     *
     * @param request - 가상계좌 입금 요청 데이터
     * @return - 결제대행사에서 가상계좌 입금 처리로 인한 응답 데이터
     */
    @Operation(summary = "가상계좌 결제 입금 확인",
               description = "회원이 주문한 건에 대해 가상계좌에 입금하게 되면 받는 알림을 처리합니다.",
               parameters = @Parameter(description = "가상계좌 입금 요청 데이터", required = true),
               responses = @ApiResponse(responseCode = "200",
                                        content = @Content(mediaType = "application/json",
                                                           schema = @Schema(implementation = ShopResult.class)),
                                        useReturnTypeSchema = true))
    @PostMapping("/payments/virtual-accounts/deposit")
    public ResponseEntity<ShopResult<String>> putMoneyInVirtualAccount(@RequestBody @Valid final
                                                                       VirtualAccountDepositRequest request) {

        log.info("putMoneyInVirtualAccount: {}", request);

        paymentService.putMoneyInVirtualAccount(request);

        return ResponseEntity.status(HttpStatus.OK)
                             .contentType(MediaType.APPLICATION_JSON)
                             .body(ShopResult.successWithDefaultMessage());
    }

    /**
     * 승인된 결제에 대해 결제 취소 요청을 처리합니다.
     *
     * @param paymentRequest - 결제 요청 데이터
     * @return 결제대행사에서 결제 승인 처리로 인한 응답 데이터
     */
    @PostMapping("/payments/{paymentKey}/cancel")
    public ResponseEntity<ShopResult<String>> cancelPayment(@PathVariable String paymentKey,
                                                            @RequestBody @Valid final
                                                            PaymentCancelRequest paymentRequest) {
        log.info("cancelPayment: {}", paymentRequest);

        paymentService.cancelPayment(paymentKey, paymentRequest);

        return ResponseEntity.status(HttpStatus.OK)
                             .contentType(MediaType.APPLICATION_JSON)
                             .body(ShopResult.successWithDefaultMessage());
    }

}
