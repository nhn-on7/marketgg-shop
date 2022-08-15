package com.nhnacademy.marketgg.server.repository.payment;

import com.nhnacademy.marketgg.server.entity.payment.MobilePhonePayment;
import org.springframework.data.repository.CrudRepository;

/**
 * 휴대폰 결제 정보에 대한 영속성을 관리합니다.
 *
 * @author 이제훈
 * @version 1.0
 * @since 1.0
 */
public interface MobilePhonePaymentRepository extends CrudRepository<MobilePhonePayment, Long> {
}
