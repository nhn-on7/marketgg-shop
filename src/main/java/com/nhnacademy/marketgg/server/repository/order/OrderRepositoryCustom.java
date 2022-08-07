package com.nhnacademy.marketgg.server.repository.order;

import com.nhnacademy.marketgg.server.entity.Member;
import com.nhnacademy.marketgg.server.entity.Order;
import java.util.List;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface OrderRepositoryCustom {

    List<Order> findOrderByMonth(Member member)  ;

}
