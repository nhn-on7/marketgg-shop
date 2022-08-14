package com.nhnacademy.marketgg.server.entity.payment;

import com.nhnacademy.marketgg.server.constant.payment.PaymentMethod;
import com.nhnacademy.marketgg.server.constant.payment.PaymentMethodConverter;
import com.nhnacademy.marketgg.server.constant.payment.PaymentStatus;
import com.nhnacademy.marketgg.server.entity.Order;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
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
 * 결제 개체입니다.
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
@Table(name = "payments")
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Getter
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "payment_no")
    private Long id;

    @OneToOne
    @JoinColumn(name = "order_no")
    // @NotNull
    private Order order;

    @Column(name = "order_name")
    @NotBlank
    @Size(max = 100)
    private String orderName;

    @Column(name = "payment_key", unique = true)
    @NotBlank
    @Size(max = 200)
    private String paymentKey;

    @Column
    @Convert(converter = PaymentMethodConverter.class)
    @NotNull
    private PaymentMethod method;

    @Column(name = "total_amount")
    @NotNull
    private Long totalAmount;

    @Column(name = "balance_amount")
    private Long balanceAmount;

    @Column(name = "discount_amount")
    private Long discountAmount;

    @Column
    @NotNull
    private PaymentStatus status;

    @Column
    @NotBlank
    @Size(max = 64)
    private String transactionKey;

    @Column(name = "created_at")
    @NotNull
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    @NotNull
    private LocalDateTime updatedAt;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

}
