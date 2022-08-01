package com.nhnacademy.marketgg.server.entity;

import com.nhnacademy.marketgg.server.dto.request.PointHistoryRequest;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 포인트 내역 개체입니다.
 *
 * @version 1.0
 * @since 1.0
 */
@Table(name = "point_histories")
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class PointHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "point_no")
    @NotNull
    private Long id;

    @ManyToOne
    @JoinColumn(name = "member_no")
    @NotNull
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_no")
    private Order order;

    @Column
    @NotNull
    private Integer point;

    @Column(name = "total_point")
    @NotNull
    private Integer totalPoint;

    @Column
    @NotNull
    @Size(min = 1, max = 100)
    private String content;

    @Column(name = "updated_at")
    @NotNull
    private LocalDateTime updatedAt;

    /**
     * 포인트 내역을 생성할 수 있는 생성자입니다.
     *
     * @param member       - 지정한 회원 Entity 입니다.
     * @param order        - 지정한 주문 Entity 입니다.
     * @param totalPoint   - 최종 포인트 입니다.
     * @param pointRequest - 포인트 내역의 생성을 위한 정보를 담은 DTO 입니다.
     */
    public PointHistory(final Member member, final Order order, final Integer totalPoint,
                        final PointHistoryRequest pointRequest) {
        this.member = member;
        this.order = order;
        this.point = pointRequest.getPoint();
        this.totalPoint = totalPoint;
        this.content = pointRequest.getContent();
        this.updatedAt = LocalDateTime.now();
    }

}
