package com.nhnacademy.marketgg.server.repository;

import com.nhnacademy.marketgg.server.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {

}
