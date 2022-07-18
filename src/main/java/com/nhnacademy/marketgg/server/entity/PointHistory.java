package com.nhnacademy.marketgg.server.entity;

import com.nhnacademy.marketgg.server.dto.request.PointHistoryRequest;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Table(name = "point_histories")
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class PointHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "point_no")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "member_no")
    private Member member;

    @ManyToOne
    @JoinColumn(name = "order_no")
    private Order order;

    @Column
    private Integer point;

    @Column(name = "total_point")
    private Integer totalPoint;

    @Column
    private String content;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    public PointHistory(final Member member, final Order order, final Integer totalPoint, final PointHistoryRequest pointRequest) {
        this.member = member;
        this.order = order;
        this.point = pointRequest.getPoint();
        this.totalPoint = totalPoint;
        this.content = pointRequest.getContent();
        this.updatedAt = pointRequest.getUpdatedAt();
    }

}
