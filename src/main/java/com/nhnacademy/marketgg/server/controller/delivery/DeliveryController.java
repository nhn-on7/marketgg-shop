package com.nhnacademy.marketgg.server.controller.delivery;

import com.nhnacademy.marketgg.server.dto.request.delivery.CreatedTrackingNoRequest;
import com.nhnacademy.marketgg.server.service.delivery.DeliveryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Client 서버에서 처리된 배송서버의 정보를 처리하기 위한 컨트롤러 입니다.
 *
 * @author 김훈민
 * @version 1.0.0
 */
@Controller
@RequiredArgsConstructor
@RequestMapping("/delivery")
public class DeliveryController {

    private final DeliveryService deliveryService;

    /**
     * Client 서버에서 받은 배송서버의 정보(운송장 번호) 를 처리하기 위한 메소드입니다.
     *
     * @param createdTrackingNoRequest - 운송장 번호와 주문 정보 입니다.
     * @return HttpStatus.OK 를 보냅니다.
     */
    @PostMapping
    public ResponseEntity<Void> createdTrackingNo(@RequestBody CreatedTrackingNoRequest createdTrackingNoRequest) {
        deliveryService.createdTrackingNo(createdTrackingNoRequest);

        return ResponseEntity.status(HttpStatus.OK)
                             .build();
    }

}
