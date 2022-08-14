package com.nhnacademy.marketgg.server.entity.payment;

import com.nhnacademy.marketgg.server.constant.payment.AcquireStatus;
import com.nhnacademy.marketgg.server.constant.payment.AgencyCode;
import com.nhnacademy.marketgg.server.constant.payment.CardType;
import com.nhnacademy.marketgg.server.constant.payment.OwnerType;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 카드 결제 개체입니다.
 *
 * @author 김정민
 * @author 김훈민
 * @author 민아영
 * @author 박세완
 * @author 윤동열
 * @author 이제훈
 * @author 조현진
 * @version 1.0
 * @since 1.0
 */
@Table(name = "card_payments")
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Getter
public class CardPayment {

    /**
     * 결제 테이블의 키를 정의합니다.
     *
     * @author 이제훈
     */
    @Embeddable
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @Getter
    @EqualsAndHashCode
    public static class Pk implements Serializable {

        @Column(name = "payment_no")
        @NotNull
        private Long paymentId;

    }

    @Id
    private Pk pk;

    @MapsId(value = "paymentId")
    @OneToOne
    @JoinColumn(name = "payment_no")
    @NotNull
    private Payment payment;

    @Column
    @NotNull
    private Long amount;

    @Column
    @Enumerated(EnumType.STRING)
    @NotBlank
    @Size(min = 2, max = 10)
    private AgencyCode companyCode;

    @Column
    @NotBlank
    @Size(min = 2, max = 10)
    private String number;

    @Column
    private Integer installmentPlanMonths;

    @Column
    @Enumerated(EnumType.STRING)
    @NotBlank
    @Size(min = 2, max = 10)
    private CardType cardType;

    @Column
    @NotBlank
    @Size(min = 2, max = 10)
    private OwnerType ownerType;

    @Column
    @NotBlank
    @Size(min = 2, max = 255)
    private String receiptUrl;

    @Column
    @NotBlank
    @Size(min = 2, max = 20)
    private AcquireStatus acquireStatus;

}
