package com.nhnacademy.marketgg.server.service.impl;

import com.nhnacademy.marketgg.server.dto.request.PointHistoryRequest;
import com.nhnacademy.marketgg.server.dto.response.PointRetrieveResponse;
import com.nhnacademy.marketgg.server.entity.Member;
import com.nhnacademy.marketgg.server.entity.Order;
import com.nhnacademy.marketgg.server.entity.PointHistory;
import com.nhnacademy.marketgg.server.event.SavePointEvent;
import com.nhnacademy.marketgg.server.exception.member.MemberNotFoundException;
import com.nhnacademy.marketgg.server.exception.order.OrderNotFoundException;
import com.nhnacademy.marketgg.server.repository.member.MemberRepository;
import com.nhnacademy.marketgg.server.repository.order.OrderRepository;
import com.nhnacademy.marketgg.server.repository.pointhistory.PointHistoryRepository;
import com.nhnacademy.marketgg.server.service.PointService;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionalEventListener;

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

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void createPointHistory(final Long id, final PointHistoryRequest pointRequest) {
        Member member = memberRepository.findById(id).orElseThrow(MemberNotFoundException::new);
        AtomicReference<Integer> totalPoint = new AtomicReference<>(0);
        pointRepository.findLastTotalPoint(member.getId())
                       .ifPresent(x -> totalPoint.set(x.getTotalPoint()));
        PointHistory pointHistory =
            new PointHistory(member, null, totalPoint.get() + pointRequest.getPoint(), pointRequest);

        pointRepository.save(pointHistory);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void createPointHistoryForOrder(final Long memberId, final Long orderId,
                                           final PointHistoryRequest pointRequest) {

        Member member = memberRepository.findById(memberId).orElseThrow(MemberNotFoundException::new);
        Order order = orderRepository.findById(orderId).orElseThrow(OrderNotFoundException::new);
        AtomicReference<Integer> totalPoint = new AtomicReference<>(0);
        pointRepository.findLastTotalPoint(member.getId())
                       .ifPresent(x -> totalPoint.set(x.getTotalPoint()));

        this.checkMemberGrade(member.getMemberGrade().getGrade(), pointRequest);

        PointHistory pointHistory =
            new PointHistory(member, order, totalPoint.get() + pointRequest.getPoint(), pointRequest);

        pointRepository.save(pointHistory);
    }

    @Async
    @TransactionalEventListener
    public void savePointByEvent(SavePointEvent point) {
        Member member = point.getMember();

        AtomicReference<Integer> totalPoint = new AtomicReference<>(0);
        pointRepository.findLastTotalPoint(member.getId())
                       .ifPresent(x -> totalPoint.set(x.getTotalPoint()));

        PointHistoryRequest pointHistoryRequest =
            new PointHistoryRequest(Math.toIntExact(point.getPoint()), point.getContent());
        PointHistory pointHistory =
            new PointHistory(member, null, totalPoint.get() + point.getPoint(), pointHistoryRequest);

        pointRepository.save(pointHistory);
    }

    private void checkMemberGrade(final String grade, final PointHistoryRequest pointRequest) {
        if ((grade.compareTo("VIP") == 0) && pointRequest.getPoint() > 0) {
            pointRequest.vipBenefit();
        } else if ((grade.compareTo("G-VIP") == 0) && pointRequest.getPoint() > 0) {
            pointRequest.gVipBenefit();
        }
    }

}
