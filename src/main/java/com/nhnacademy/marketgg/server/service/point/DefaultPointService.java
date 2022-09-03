package com.nhnacademy.marketgg.server.service.point;

import com.nhnacademy.marketgg.server.dto.request.point.PointHistoryRequest;
import com.nhnacademy.marketgg.server.dto.response.point.PointRetrieveResponse;
import com.nhnacademy.marketgg.server.entity.Member;
import com.nhnacademy.marketgg.server.entity.Order;
import com.nhnacademy.marketgg.server.entity.PointHistory;
import com.nhnacademy.marketgg.server.exception.member.MemberNotFoundException;
import com.nhnacademy.marketgg.server.exception.order.OrderNotFoundException;
import com.nhnacademy.marketgg.server.repository.member.MemberRepository;
import com.nhnacademy.marketgg.server.repository.order.OrderRepository;
import com.nhnacademy.marketgg.server.repository.pointhistory.PointHistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class DefaultPointService implements PointService {

    private final PointHistoryRepository pointRepository;
    private final MemberRepository memberRepository;
    private final OrderRepository orderRepository;

    @Override
    public Page<PointRetrieveResponse> retrievePointHistories(final Long id, final Pageable pageable) {
        return pointRepository.findAllByMemberId(id, pageable);
    }

    @Override
    public Page<PointRetrieveResponse> adminRetrievePointHistories(final Pageable pageable) {
        return pointRepository.findAllForAdmin(pageable);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void createPointHistory(final Long id, final PointHistoryRequest pointRequest) {
        Member member = memberRepository.findById(id).orElseThrow(MemberNotFoundException::new);
        Integer totalPoint = pointRepository.findLastTotalPoints(member.getId());
        PointHistory pointHistory =
                new PointHistory(member, null, totalPoint + pointRequest.getPoint(), pointRequest);

        pointRepository.save(pointHistory);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void createPointHistoryForOrder(final Long memberId, final Long orderId,
                                           final PointHistoryRequest pointRequest) {

        Member member = memberRepository.findById(memberId).orElseThrow(MemberNotFoundException::new);
        Order order = orderRepository.findById(orderId).orElseThrow(OrderNotFoundException::new);
        Integer totalPoint = pointRepository.findLastTotalPoints(member.getId());

        this.checkMemberGrade(member.getMemberGrade().getGrade(), pointRequest);

        PointHistory pointHistory =
                new PointHistory(member, order, totalPoint + pointRequest.getPoint(), pointRequest);

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
