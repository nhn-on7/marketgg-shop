package com.nhnacademy.marketgg.server.repository;

import com.nhnacademy.marketgg.server.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment, Long> {

}
