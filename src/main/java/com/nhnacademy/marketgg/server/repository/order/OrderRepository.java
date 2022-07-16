package com.nhnacademy.marketgg.server.repository.order;

import com.nhnacademy.marketgg.server.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long>, OrderRepositoryCustom {

}
