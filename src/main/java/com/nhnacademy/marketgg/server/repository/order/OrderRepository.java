package com.nhnacademy.marketgg.server.repository.order;

import com.nhnacademy.marketgg.server.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 주문 Repository 입니다.
 *
 * @version 1.0.0
 * @author 김정민
 */
public interface OrderRepository extends JpaRepository<Order, Long>, OrderRepositoryCustom {

}
