package com.nhnacademy.marketgg.server.repository.payment;

import com.nhnacademy.marketgg.server.entity.payment.Payment;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 결제 저장소입니다.
 *
 * @author 이제훈
 */
public interface PaymentRepository extends JpaRepository<Payment, Long>, PaymentRepositoryCustom {

    Optional<Payment> findByPaymentKey(String paymentKey);

}
