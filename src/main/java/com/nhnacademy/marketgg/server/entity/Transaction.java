package com.nhnacademy.marketgg.server.entity;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

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
import java.io.Serializable;
import java.time.LocalDateTime;

@Table(name = "transactions")
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Transaction {

    @EmbeddedId
    private Pk pk;

    @MapsId("paymentNo")
    @ManyToOne
    @JoinColumn
    private Payment payment;

    @Column(name = "step_type")
    private String stepType;

    @Column(name = "transaction_amount")
    private Long transactionAmount;

    @Column(name = "discount_amount")
    private Long discountAmount;

    @Column(name = "paid_amount")
    private Long paidAmount;

    @Column(name = "reg_ts")
    private LocalDateTime regTs;

    @Embeddable
    @EqualsAndHashCode
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @Getter
    public static class Pk implements Serializable {

        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "transaction_no")
        private Long transactionNo;

        @Column(name = "payment_no")
        private Long paymentNo;

    }

}
