package com.nhnacademy.marketgg.server.entity.payment;

import com.nhnacademy.marketgg.server.constant.payment.BankCode;
import com.nhnacademy.marketgg.server.constant.payment.SettlementStatus;
import com.nhnacademy.marketgg.server.constant.payment.converter.BankCodeConverter;
import com.nhnacademy.marketgg.server.constant.payment.converter.SettlementStatusConveter;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 계좌이체로 결제했을 때 이체 정보가 담기는 개체입니다.
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
@Table(name = "transfer_payments")
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Getter
public class TransferPayment {

    @Id
    private Long paymentId;

    @MapsId
    @OneToOne
    @JoinColumn(name = "payment_no")
    @NotNull
    private Payment payment;

    @Column
    @Convert(converter = BankCodeConverter.class)
    @NotNull
    private BankCode bank;

    @Column(name = "settlement_status")
    @Convert(converter = SettlementStatusConveter.class)
    @NotNull
    private SettlementStatus settlementStatus;

}
