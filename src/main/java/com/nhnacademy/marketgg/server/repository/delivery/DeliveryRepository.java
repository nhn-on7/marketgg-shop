package com.nhnacademy.marketgg.server.repository.delivery;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.nhnacademy.marketgg.server.dto.request.order.OrderInfoRequestDto;
import org.springframework.http.ResponseEntity;

/**
 * delivery 서버와의 전송을 위한 Repository 입니다.
 *
 * @author 김정민
 * @version 1.0.0
 */
public interface DeliveryRepository {

    /**
     * 주문의 운송장 번호를 발급받기 위한 메소드입니다.
     *
     * @param orderInfoRequestDto - 운송장 번호를 발급받기 위해 주문의 정보를 전송합니다.
     * @return 요청에 대한 응답객체를 반환합니다.
     * @throws JsonProcessingException - Json 컨텐츠를 처리할 때 발생하는 모든 문제에 대한 예외처리입니다.
     */
    ResponseEntity<Void> createTrackingNo(OrderInfoRequestDto orderInfoRequestDto) throws JsonProcessingException;

}
