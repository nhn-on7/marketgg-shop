package com.nhnacademy.marketgg.server.entity.payment;

import com.nhnacademy.marketgg.server.constant.payment.AcquireStatus;
import com.nhnacademy.marketgg.server.constant.payment.AgencyCode;
import com.nhnacademy.marketgg.server.constant.payment.AgencyCodeConverter;
import com.nhnacademy.marketgg.server.constant.payment.CardType;
import com.nhnacademy.marketgg.server.constant.payment.CardTypeConverter;
import com.nhnacademy.marketgg.server.constant.payment.OwnerType;
import com.nhnacademy.marketgg.server.constant.payment.OwnerTypeConverter;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
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
public class CardPayment implements Serializable {

    @Id
    private Long paymentId;

    @MapsId
    @OneToOne
    @JoinColumn(name = "payment_no")
    @NotNull
    private Payment payment;

    @Column
    @NotNull
    private Long amount;

    @Column
    @Convert(converter = AgencyCodeConverter.class)
    @NotNull
    private AgencyCode companyCode;

    @Column
    @NotBlank
    @Size(min = 2, max = 20)
    private String number;

    @Column
    @NotNull
    private Integer installmentPlanMonths;

    @Column
    @Convert(converter = CardTypeConverter.class)
    @NotNull
    private CardType cardType;

    @Column
    @Convert(converter = OwnerTypeConverter.class)
    private OwnerType ownerType;

    @Column
    @NotBlank
    @Size(min = 2, max = 255)
    private String receiptUrl;

    @Column
    @NotNull
    private AcquireStatus acquireStatus;

}
