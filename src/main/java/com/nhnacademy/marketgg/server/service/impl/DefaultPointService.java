package com.nhnacademy.marketgg.server.service.impl;

import com.nhnacademy.marketgg.server.dto.request.PointHistoryRequest;
import com.nhnacademy.marketgg.server.dto.response.PointRetrieveResponse;
import com.nhnacademy.marketgg.server.entity.Member;
import com.nhnacademy.marketgg.server.entity.Order;
import com.nhnacademy.marketgg.server.entity.PointHistory;
import com.nhnacademy.marketgg.server.exception.member.MemberNotFoundException;
import com.nhnacademy.marketgg.server.exception.order.OrderNotFoundException;
import com.nhnacademy.marketgg.server.repository.member.MemberRepository;
import com.nhnacademy.marketgg.server.repository.order.OrderRepository;
import com.nhnacademy.marketgg.server.repository.pointhistory.PointHistoryRepository;
import com.nhnacademy.marketgg.server.service.PointService;
import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class DefaultPointService implements PointService {

    private final PointHistoryRepository pointRepository;
    private final MemberRepository memberRepository;
    private final OrderRepository orderRepository;

    @Override
    public List<PointRetrieveResponse> retrievePointHistories(final Long id) {
        return pointRepository.findAllByMemberId(id);
    }

    @Override
    public List<PointRetrieveResponse> adminRetrievePointHistories() {
        return pointRepository.findAllForAdmin();
    }

    @Transactional
    @Override
    public void createPointHistory(final Long id, final PointHistoryRequest pointRequest) {
        Member member = memberRepository.findById(id).orElseThrow(MemberNotFoundException::new);
        Integer totalPoint = pointRepository.findLastTotalPoint(id);
        PointHistory pointHistory = new PointHistory(member, null, totalPoint + pointRequest.getPoint(), pointRequest);

        pointRepository.save(pointHistory);
    }

    @Transactional
    @Override
    public void createPointHistoryForOrder(final Long memberId, final Long orderId,
                                           final PointHistoryRequest pointRequest) {

        Member member = memberRepository.findById(memberId).orElseThrow(MemberNotFoundException::new);
        Order order = orderRepository.findById(orderId).orElseThrow(OrderNotFoundException::new);
        Integer totalPoint = pointRepository.findLastTotalPoint(memberId);

        if (Objects.equals(member.getMemberGrade().getGrade(), "VIP") &&
                pointRequest.getPoint() > 0) {
            pointRequest.isVip();
        } else if (Objects.equals(member.getMemberGrade().getGrade(), "G-VIP") &&
                pointRequest.getPoint() > 0) {
            pointRequest.isGVip();
        }

        PointHistory pointHistory = new PointHistory(member, order, totalPoint + pointRequest.getPoint(), pointRequest);

        pointRepository.save(pointHistory);
    }

}
