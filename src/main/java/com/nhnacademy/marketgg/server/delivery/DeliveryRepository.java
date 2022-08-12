package com.nhnacademy.marketgg.server.delivery;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.nhnacademy.marketgg.server.dto.info.OrderInfoRequestDto;
import org.springframework.http.ResponseEntity;

/**
 * delivery 서버와의 전송을 위한 Repository 입니다.
 *
 * @author 김정민
 * @version 1.0.0
 */
public interface DeliveryRepository {

    ResponseEntity<Void> createTrackingNo(OrderInfoRequestDto orderInfoRequestDto) throws JsonProcessingException;

}
