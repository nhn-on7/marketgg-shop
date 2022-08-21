package com.nhnacademy.marketgg.server.eventlistener.event.savepoint;

import com.nhnacademy.marketgg.server.dto.request.point.PointHistoryRequest;
import com.nhnacademy.marketgg.server.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 포인트 적립에 필요한 Data 를 가진 이벤트 추상클래스 입니다.
 *
 * @author 조현진
 * @author 민아영
 * @version 1.0.0
 * @since 1.0.0
 */
@AllArgsConstructor
@Getter
public abstract class SavePointEvent {

    private Member member;
    private String content;

    /**
     * 적립할 포인트의 요청 Dto 를 반환하는 메소드 입니다.
     *
     * @return 적립할 포인트의 Dto 를 반환합니다.
     * @author 민아영
     * @since 1.0.0
     */
    public abstract PointHistoryRequest getPointHistory();

}
