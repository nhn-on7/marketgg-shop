package com.nhnacademy.marketgg.server.repository.payment;

import com.nhnacademy.marketgg.server.dto.payment.request.PaymentCancelRequest;
import com.nhnacademy.marketgg.server.dto.payment.request.PaymentConfirmRequest;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

/**
 * 결제대행사에 요청을 담당합니다.
 *
 * @author 이제훈
 * @version 1.0
 * @since 1.0
 */
@Component
@RequiredArgsConstructor
public class PaymentAdapter {

    private final RestTemplate restTemplate;

    @Value("${gg.tosspayments.test.secret-key}")
    private String testSecretKey;

    @Value("${gg.tosspayments.origin}")
    private String tosspaymentsOrigin;

    /**
     * 결제대행사에 결제 승인 요청을 전송합니다.
     *
     * @param paymentRequest - 결제 요청 정보
     * @return 결제 승인 응답 정보
     */
    public ResponseEntity<String> confirm(final PaymentConfirmRequest paymentRequest) {
        Map<String, Object> params = new HashMap<>();
        params.put("orderId", paymentRequest.getOrderId());
        params.put("amount", paymentRequest.getAmount());
        params.put("paymentKey", paymentRequest.getPaymentKey());
        JSONObject body = new JSONObject(params);

        return restTemplate.postForEntity(tosspaymentsOrigin + "/payments/confirm",
                                          new HttpEntity<>(body, this.getHttpHeaders()), String.class);
    }

    public ResponseEntity<String> retrievePayment(String paymentKey) {
        Map<String, Object> params = new HashMap<>();
        params.put("paymentKey", paymentKey);
        JSONObject body = new JSONObject(params);

        return restTemplate.postForEntity(tosspaymentsOrigin + "/payments/confirm",
                                          new HttpEntity<>(body, this.getHttpHeaders()), String.class);
    }

    /**
     * 결제대행사에 결제 취소 요청을 전송합니다.
     *
     * @param paymentKey     - 결제 건에 대한 고유 키 값
     * @param paymentRequest - 결제 취소 요청 정보
     * @return 결제 취소 응답 정보
     */
    public ResponseEntity<String> cancel(final String paymentKey, final PaymentCancelRequest paymentRequest) {
        Map<String, String> params = new HashMap<>();
        params.put("cancelReason", paymentRequest.getCancelReason());
        JSONObject body = new JSONObject(params);

        return restTemplate.postForEntity(tosspaymentsOrigin + "/payments/" + paymentKey + "/cancel",
                                          new HttpEntity<>(body, this.getHttpHeaders()), String.class);
    }

    private HttpHeaders getHttpHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setBasicAuth(this.getEncodedPaymentKey());
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

        return headers;
    }

    private String getEncodedPaymentKey() {
        return new String(Base64.getEncoder().encode((this.testSecretKey + ":").getBytes(StandardCharsets.UTF_8)));
    }

}
