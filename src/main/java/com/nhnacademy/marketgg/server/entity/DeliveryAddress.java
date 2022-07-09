package com.nhnacademy.marketgg.server.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;
import java.io.Serializable;

@Table(name = "delivery_addresses")
@Entity
@NoArgsConstructor
@Getter
public class DeliveryAddress {

    @EmbeddedId
    private DeliveryAddressPk pk;

    @MapsId("memberNo")
    @ManyToOne
    @JoinColumn(name = "member_no")
    private Member member;

    @Column(name = "is_default_address")
    private Boolean isDefaultAddress;

    @Column(name = "zip_code")
    private Integer zipCode;

    @Column(name = "road_name_address")
    private String roadNameAddress;

    @Column(name = "detail_address")
    private String detailAddress;

    @Embeddable
    @EqualsAndHashCode
    @NoArgsConstructor
    @Getter
    public static class DeliveryAddressPk implements Serializable {

        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "delivery_address_no")
        private Long deliveryAddressNo;

        @Column(name = "member_no")
        private Long memberNo;

    }

}
