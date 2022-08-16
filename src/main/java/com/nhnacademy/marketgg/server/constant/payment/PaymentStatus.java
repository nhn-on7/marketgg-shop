package com.nhnacademy.marketgg.server.constant.payment;

import java.util.Arrays;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * 결제 처리 상태를 나타내는 상수 클래스입니다.
 *
 * @author 이제훈
 * @version 1.0
 * @since 1.0
 */
@RequiredArgsConstructor
@Getter
public enum PaymentStatus {

    READY("준비됨"),
    IN_PROGRESS("진행중"),
    WAITING_FOR_DEPOSIT("가상계좌 입금 대기 중"),
    DONE("결제 완료됨"),
    CANCELED("결제가 취소됨"),
    PARTIAL_CANCELED("결제가 부분 취소됨"),
    ABORTED("카드 자동 결제 혹은 키인 결제를 할 때 결제 승인에 실패함"),
    EXPIRED("유효 시간(30분)이 지나 거래가 취소됨");

    private final String name;

    /**
     * 문자열로 구성된 결제 처리 상태를 통해 열거형을 추출합니다.
     *
     * @param status - 문자열로 구성된 결제 처리 상태
     * @return 열거형 타입의 결제 처리 상태 {@link PaymentStatus}
     */
    public static PaymentStatus of(String status) {
        return Arrays.stream(PaymentStatus.values())
                     .filter(v -> v.getName().equals(status))
                     .findAny()
                     .orElseThrow(IllegalArgumentException::new);
    }

}
