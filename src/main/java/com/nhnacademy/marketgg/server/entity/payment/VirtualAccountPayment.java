package com.nhnacademy.marketgg.server.entity.payment;

import com.nhnacademy.marketgg.server.constant.payment.BankCodeConverter;
import com.nhnacademy.marketgg.server.constant.payment.AccountType;
import com.nhnacademy.marketgg.server.constant.payment.AccountTypeConverter;
import com.nhnacademy.marketgg.server.constant.payment.BankCode;
import com.nhnacademy.marketgg.server.constant.payment.RefundStatus;
import com.nhnacademy.marketgg.server.constant.payment.SettlementStatus;
import com.nhnacademy.marketgg.server.constant.payment.SettlementStatusConveter;
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
 * 가상계좌로 결제하면 제공되는 가상계좌 관련 정보를 담고 있는 가상계좌 결제 개체입니다.
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
@Table(name = "virtual_account_payments")
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Getter
public class VirtualAccountPayment {

    @Id
    private Long paymentId;

    @MapsId
    @OneToOne
    @JoinColumn(name = "payment_no")
    @NotNull
    private Payment payment;

    @Column(name = "account_type")
    @Convert(converter = AccountTypeConverter.class)
    @NotNull
    private AccountType accountType;

    @Column(name = "account_number")
    @NotBlank
    @Size(max = 20)
    private String accountNumber;

    @Column
    @Convert(converter = BankCodeConverter.class)
    @NotNull
    private BankCode bank;

    @Column(name = "customer_name")
    @NotBlank
    @Size(max = 100)
    private String customerName;

    @Column(name = "due_date")
    @NotBlank
    // @Size(min = 3, max = 21)
    private String dueDate;

    @Column(name = "refund_status")
    @NotNull
    private RefundStatus refundStatus;

    @Column(columnDefinition = "TINYINT", length = 1)
    private boolean expired;

    @Column(name = "settlement_status")
    @Convert(converter = SettlementStatusConveter.class)
    @NotNull
    private SettlementStatus settlementStatus;

}
