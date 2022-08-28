package com.nhnacademy.marketgg.server.dummy;

import com.nhnacademy.marketgg.server.dto.payment.PaymentResponse;
import com.nhnacademy.marketgg.server.dto.payment.request.PaymentCancelRequest;
import com.nhnacademy.marketgg.server.dto.payment.request.PaymentConfirmRequest;
import com.nhnacademy.marketgg.server.dto.payment.request.VirtualAccountCreateRequest;
import com.nhnacademy.marketgg.server.dto.payment.request.VirtualAccountDepositRequest;
import com.nhnacademy.marketgg.server.dto.payment.result.CardPaymentResult;
import com.nhnacademy.marketgg.server.dto.payment.result.Receipt;
import com.nhnacademy.marketgg.server.dto.response.order.OrderToPayment;
import com.nhnacademy.marketgg.server.entity.payment.Payment;
import java.time.LocalDateTime;
import java.util.Optional;
import org.springframework.test.util.ReflectionTestUtils;

public class PaymentDummy {

    public static final String PAYMENT_KEY = "EAK6k75XwlOyL0qZ4G1VOP4xk47qOroWb2MQYgmBDPdR9pxz";

    public static OrderToPayment getOrderToPayment() {
        return new OrderToPayment("GGORDER_1", "orderName", "강태풍",
                                  "strong.storm@gmail.com", 30_000L, 1L,
                                  2_000, 300);
    }

    public static PaymentConfirmRequest getPaymentConfirmRequest() {
        return new PaymentConfirmRequest("GGORDER_1", PAYMENT_KEY, 3_200L);
    }

    public static PaymentCancelRequest getPaymentCancelRequest() {
        return new PaymentCancelRequest("단순 변심", "신한", "110377904929", "강태풍");
    }

    public static Payment createPayment() {
        return Payment.builder()
                      .build();
    }

    public static PaymentConfirmRequest createPaymentConfirmRequest() {
        return new PaymentConfirmRequest("GGORDER_1", PAYMENT_KEY, 75_600L);
    }

    public static VirtualAccountCreateRequest getVirtualAccountCreateRequest() {
        return new VirtualAccountCreateRequest(75_600L, "신한", "강태풍",
                                               "GGORDER_1", "[프렙] 쉬림프 로제 리조또 외 3건", 6);
    }

    public static VirtualAccountDepositRequest getVirtualAccountDepositRequest() {
        return new VirtualAccountDepositRequest(LocalDateTime.parse("2022-01-01T00:00:00.000"),
                                                "AQItPnF1QIJ_hQ1vt4BnI", "DONE",
                                                "9FF15E1A29D0E77C218F57262BFA4986", "GGORDER_1");
    }

    public static PaymentResponse createPaymentResponse(String method) {
        PaymentResponse paymentResponse = new PaymentResponse();
        ReflectionTestUtils.setField(paymentResponse, "paymentKey", PAYMENT_KEY);
        ReflectionTestUtils.setField(paymentResponse, "orderId", "GGORDER_1");
        ReflectionTestUtils.setField(paymentResponse, "orderName", "[프렙] 쉬림프 로제 리조또 외 3건");
        ReflectionTestUtils.setField(paymentResponse, "status", "DONE");
        ReflectionTestUtils.setField(paymentResponse, "transactionKey", "0A25AEB83EB828C7CBDB3E0C97834557");
        ReflectionTestUtils.setField(paymentResponse, "requestedAt", "2022-01-01T00:00:00+09:00");
        ReflectionTestUtils.setField(paymentResponse, "approvedAt", "2022-01-01T00:00:00+09:00");
        ReflectionTestUtils.setField(paymentResponse, "totalAmount", 75_600L);
        ReflectionTestUtils.setField(paymentResponse, "balanceAmount", 100L);
        ReflectionTestUtils.setField(paymentResponse, "method", Optional.ofNullable(method).orElse("카드"));
        // ReflectionTestUtils.setField(paymentResponse, "card", getCardPaymentResult());

        return paymentResponse;
    }

    private static CardPaymentResult getCardPaymentResult() {
        CardPaymentResult result = new CardPaymentResult();
        ReflectionTestUtils.setField(result, "amount", 75_600L);
        ReflectionTestUtils.setField(result, "companyCode", "신한");
        ReflectionTestUtils.setField(result, "number", "42215500****581*");
        ReflectionTestUtils.setField(result, "cardType", "신용");
        ReflectionTestUtils.setField(result, "ownerType", "개인");
        ReflectionTestUtils.setField(result, "receiptUrl",
                                     "https://dashboard.tosspayments.com/sales-slip?transactionId=v%2F0NtjvAdMVLEtqHnVUFzF6UozHOy5iHiaKp7u%2FrARSyQKnU6qMgnhS%2BBJ5evY%2BY&ref=PX");
        ReflectionTestUtils.setField(result, "acquireStatus", "READY");

        return result;
    }

    private static Receipt getReceipt() {
        Receipt receipt = new Receipt();
        ReflectionTestUtils.setField(receipt, "url",
                                     "https://dashboard.tosspayments.com/sales-slip?transactionId=v%2F0NtjvAdMVLEtqHnVUFzF6UozHOy5iHiaKp7u%2FrARSyQKnU6qMgnhS%2BBJ5evY%2BY&ref=PX");

        return receipt;
    }

    public static String cardResult() {
        return "\"card\":{\"company\":\"신한\",\"number\":\"42215500****581*\",\"installmentPlanMonths\":0,\"isInterestFree\":false,\"interestPayer\":null,\"approveNo\":\"00000000\",\"useCardPoint\":false,\"cardType\":\"신용\",\"ownerType\":\"개인\",\"acquireStatus\":\"READY\",\"receiptUrl\":\"https://dashboard.tosspayments.com/sales-slip?transactionId=v%2F0NtjvAdMVLEtqHnVUFzF6UozHOy5iHiaKp7u%2FrARSyQKnU6qMgnhS%2BBJ5evY%2BY&ref=PX\",\"amount\":200},\"virtualAccount\":null,\"transfer\":null,\"mobilePhone\":null,";
    }

    public static String getPaymentResponse(String paymentTypeResult) {
        return "{\"mId\":\"tvivarepublica\",\"transactionKey\":\"0A25AEB83EB828C7CBDB3E0C97834557\"," +
            "\"lastTransactionKey\":\"0A25AEB83EB828C7CBDB3E0C97834557\"," +
            "\"paymentKey\":\"5zJ4xY7m0kODnyRpQWGrNDLByYW0g3Kwv1M9ENjbeoPaZdL6\"," +
            "\"orderId\":\"GGORDER_2\",\"orderName\":\"[KF365]아보카도200g(1걔)외2건\",\"status\":\"DONE\"," +
            "\"requestedAt\":\"2022-08-11T01:12:54+09:00\",\"approvedAt\":\"2022-08-11T01:13:18+09:00\"," +
            "\"useEscrow\":false,\"cultureExpense\":false," +
            paymentTypeResult +
            "\"giftCertificate\":null,\"cashReceipt\":null,\"discount\":null,\"cancels\":null," +
            "\"secret\":\"ps_5GePWvyJnrK24lwpZyqVgLzN97Eo\",\"type\":\"NORMAL\",\"easyPay\":null,\"country\":\"KR\"," +
            "\"failure\":null," +
            "\"receipt\":{\"url\":\"https://dashboard.tosspayments.com/sales-slip?transactionId=duOfMjWI%2BxoZzSJ5VRibnUDEDnIUwV4tHtum0QzTA5nD1wiIrfzZXB5kXkjm4VD5&ref=PX\"},\"checkout\":{\"url\":\"https://api.tosspayments.com/v1/payments/5zJ4xY7m0kODnyRpQWGrNDLByYW0g3Kwv1M9ENjbeoPaZdL6/checkout\"}," +
            "\"currency\":\"KRW\",\"totalAmount\":200,\"balanceAmount\":200,\"suppliedAmount\":182," +
            "\"vat\":18,\"taxFreeAmount\":0,\"method\":\"카드\",\"version\":\"2022-07-27\"}";
    }

}
