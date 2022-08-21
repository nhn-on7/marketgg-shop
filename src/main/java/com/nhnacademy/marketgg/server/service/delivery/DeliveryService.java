package com.nhnacademy.marketgg.server.service.delivery;

import com.nhnacademy.marketgg.server.dto.request.delivery.CreatedTrackingNoRequest;

/**
 * Client 서버에서 처리된 배송서버의 정보를 처리하기 위한 서비스 인터페이스 입니다.
 *
 * @author 김훈민
 * @version 1.0.0
 */
public interface DeliveryService {

    /**
     * Client 서버에서 받은 배송서버의 정보(운송장 번호) 를 처리하기 위한 메소드입니다.
     *
     * @param createdTrackingNoRequest - 운송장 번호와 주문 정보 입니다
     */
    void createdTrackingNo(final CreatedTrackingNoRequest createdTrackingNoRequest);

}
