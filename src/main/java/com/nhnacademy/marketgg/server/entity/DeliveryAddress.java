package com.nhnacademy.marketgg.server.entity;

import com.nhnacademy.marketgg.server.dto.request.member.ShopMemberSignUpRequest;
import java.io.Serializable;
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

/**
 * 배송 주소 개체입니다.
 *
 * @author 공통
 * @version 1.0.0
 */
@Table(name = "delivery_addresses")
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class DeliveryAddress {

    @EmbeddedId
    private Pk pk;

    @MapsId("memberNo")
    @ManyToOne
    @JoinColumn(name = "member_no")
    private Member member;

    @Column(name = "is_default_address")
    private Boolean isDefaultAddress;

    @Column(name = "zip_code")
    private Integer zipCode;

    @Column
    private String address;

    @Column(name = "detail_address")
    private String detailAddress;

    @Embeddable
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @Getter
    @EqualsAndHashCode
    public static class Pk implements Serializable {

        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "delivery_address_no")
        private Long deliveryAddressNo;

        @Column(name = "member_no")
        private Long memberNo;

        public Pk(Long memberNo) {
            this.memberNo = memberNo;
        }
    }

    public DeliveryAddress(final Member signUpMember,
                           final ShopMemberSignUpRequest shopMemberSignUpRequest) {

        this.pk = new Pk(signUpMember.getId());
        this.member = signUpMember;
        this.isDefaultAddress = Boolean.TRUE;
        this.zipCode = shopMemberSignUpRequest.getZipcode();
        this.address = shopMemberSignUpRequest.getAddress();
        this.detailAddress = shopMemberSignUpRequest.getDetailAddress();
    }

}
