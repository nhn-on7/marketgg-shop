package com.nhnacademy.marketgg.server.entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 주문 배송지 개체입니다.
 *
 * @version 1.0
 * @since 1.0
 */
@Table(name = "order_delivery_addresses")
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class OrderDeliveryAddress implements Serializable {

    @EmbeddedId
    @NotNull
    private Pk pk;

    @MapsId(value = "orderNo")
    @OneToOne
    @JoinColumn(name = "order_no")
    @NotNull
    private Order order;

    @ManyToOne
    @JoinColumn(name = "delivery_address_no")
    @JoinColumn(name = "member_no")
    @NotNull
    private DeliveryAddress deliveryAddress;

    @Column(name = "zip_code")
    @NotNull
    private Integer zipCode;

    @Column(name = "road_name_address")
    @NotNull
    @Size(min = 1, max = 255)
    private String roadNameAddress;

    @Column(name = "detail_address")
    @NotNull
    @Size(min = 1, max = 255)
    private String detailAddress;

    @Embeddable
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @Getter
    @EqualsAndHashCode
    public static class Pk implements Serializable {

        @Column(name = "order_no")
        @NotNull
        private Long orderNo;

    }

}
