package com.nhnacademy.marketgg.server.entity.payment;

import com.nhnacademy.marketgg.server.constant.payment.SettlementStatus;
import com.nhnacademy.marketgg.server.constant.payment.SettlementStatusConveter;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Embeddable;
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
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 *
 */
@Table(name = "mobile_phone_payments")
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Getter
public class MobilePhonePayment {

    @Id
    private Long paymentId;

    @MapsId
    @OneToOne
    @JoinColumn(name = "payment_no")
    @NotNull
    private Payment payment;

    @Column(name = "customer_mobile_phone")
    @NotBlank
    private String customerMobilePhone;

    @Column(name = "settlement_status")
    @Convert(converter = SettlementStatusConveter.class)
    @NotNull
    private SettlementStatus settlementStatus;

    @Column(name = "receipt_url")
    private String receiptUrl;

}
