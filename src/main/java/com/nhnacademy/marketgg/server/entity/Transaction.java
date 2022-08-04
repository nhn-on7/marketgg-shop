package com.nhnacademy.marketgg.server.entity;

import java.io.Serializable;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 거래 개체입니다.
 *
 * @author 공통
 * @version  1.0.0
 */
@Table(name = "transactions")
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Transaction {

    @EmbeddedId
    @NotNull
    private Pk pk;

    @MapsId("paymentNo")
    @ManyToOne
    @JoinColumn(name = "payment_no")
    @NotNull
    private Payment payment;

    @Column(name = "step_type")
    @NotBlank
    @Size(min = 1, max = 10)
    private String stepType;

    @Column(name = "transaction_amount")
    @NotNull
    private Long transactionAmount;

    @Column(name = "discount_amount")
    @NotNull
    private Long discountAmount;

    @Column(name = "paid_amount")
    @NotNull
    private Long paidAmount;

    @Column(name = "reg_ts")
    @NotNull
    private LocalDateTime regTs;

    @Embeddable
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @Getter
    @EqualsAndHashCode
    public static class Pk implements Serializable {

        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "transaction_no")
        @NotNull
        private Long transactionNo;

        @Column(name = "payment_no")
        @NotNull
        private Long paymentNo;

    }

}
