package com.nhnacademy.marketgg.server.entity.payment;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
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

    @Embeddable
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @Getter
    @EqualsAndHashCode
    public static class Pk implements Serializable {

        @Column(name = "payment_no")
        private Long paymentId;

    }

    @Id
    private Pk pk;

    @MapsId(value = "paymentId")
    @OneToOne
    @JoinColumn(name = "payment_no")
    private Payment payment;

    @Column(name = "customer_mobile_phone")
    @NotBlank
    private String customerMobilePhone;

    @Column(name = "settlement_status")
    @NotBlank
    @Size(min = 2, max = 10)
    private String settlementStatus;

    @Column(name = "receipt_url")
    private String receiptUrl;

}
