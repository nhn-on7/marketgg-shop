package com.nhnacademy.marketgg.server.repository.payment;

import com.nhnacademy.marketgg.server.dto.payment.request.PaymentCancelRequest;
import com.nhnacademy.marketgg.server.dto.payment.request.PaymentRequest;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Collections;
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
 */
@Component
@RequiredArgsConstructor
public class PaymentAdapter {

    private final RestTemplate restTemplate;

    @Value("${gg.tosspayments.test.secret-key}")
    private String testSecretKey;

    /**
     * 결제대행사에 결제 승인 요청을 전송합니다.
     *
     * @param paymentRequest - 결제 요청 정보
     * @return 결제 승인 응답 정보
     */
    public ResponseEntity<String> confirm(PaymentRequest paymentRequest) {
        Base64.Encoder encoder = Base64.getEncoder();
        String encodedKey = new String(encoder.encode((this.testSecretKey + ":").getBytes(StandardCharsets.UTF_8)));

        HttpHeaders headers = new HttpHeaders();
        headers.setBasicAuth(encodedKey);
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

        JSONObject param = new JSONObject();
        param.put("orderId", paymentRequest.getOrderId());
        param.put("amount", paymentRequest.getAmount());
        param.put("paymentKey", paymentRequest.getPaymentKey());

        return restTemplate.postForEntity("https://api.tosspayments.com/v1/payments/confirm",
                                          new HttpEntity<>(param, headers), String.class);
    }

    public ResponseEntity<String> cancel(String paymentKey, PaymentCancelRequest paymentRequest) {
        Base64.Encoder encoder = Base64.getEncoder();
        String encodedKey = new String(encoder.encode((this.testSecretKey + ":").getBytes(StandardCharsets.UTF_8)));

        HttpHeaders headers = new HttpHeaders();
        headers.setBasicAuth(encodedKey);
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

        JSONObject param = new JSONObject();
        param.put("cancelReason", paymentRequest.getCancelReason());

        return restTemplate.postForEntity("https://api.tosspayments.com/v1/payments/" + paymentKey + "/cancel",
                                          new HttpEntity<>(param, headers), String.class);
    }

}
