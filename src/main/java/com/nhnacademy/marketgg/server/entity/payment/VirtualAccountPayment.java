package com.nhnacademy.marketgg.server.entity.payment;

import com.nhnacademy.marketgg.server.constant.payment.AccountType;
import com.nhnacademy.marketgg.server.constant.payment.BankCode;
import com.nhnacademy.marketgg.server.constant.payment.RefundStatus;
import com.nhnacademy.marketgg.server.constant.payment.SettlementStatus;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
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
@Getter
public class VirtualAccountPayment {

    /**
     * 결제 테이블의 키를 정의합니다.
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

    @EmbeddedId
    private Pk pk;

    @MapsId(value = "paymentId")
    @OneToOne
    @JoinColumn(name = "payment_no")
    @NotNull
    private Payment payment;

    @Column(name = "account_type")
    @NotNull
    private AccountType accountType;

    @Column(name = "account_number")
    @NotBlank
    @Size(max = 20)
    private String accountNumber;

    @Column
    @Enumerated(EnumType.STRING)
    @NotBlank
    @Size(max = 20)
    private BankCode bank;

    @Column(name = "customer_name")
    @NotBlank
    @Size(max = 100)
    private String customerName;

    @Column(name = "due_date")
    @NotBlank
    @Size(max = 20)
    private String dueDate;

    @Column(name = "refund_status")
    @NotNull
    private RefundStatus refundStatus;

    @Column
    private String expired;

    @Column(name = "settlement_status")
    @NotNull
    private SettlementStatus settlementStatus;

}
