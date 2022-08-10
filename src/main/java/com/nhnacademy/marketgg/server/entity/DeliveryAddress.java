package com.nhnacademy.marketgg.server.entity;

import com.nhnacademy.marketgg.server.dto.request.member.ShopMemberSignUpRequest;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "delivery_address_no")
    private Long id;

    @ManyToOne(cascade = CascadeType.REMOVE)
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


    public DeliveryAddress(final Member signUpMember,
                           final ShopMemberSignUpRequest shopMemberSignUpRequest) {


        this.member = signUpMember;
        this.isDefaultAddress = true;
        this.zipCode = shopMemberSignUpRequest.getZipcode();
        this.address = shopMemberSignUpRequest.getAddress();
        this.detailAddress = shopMemberSignUpRequest.getDetailAddress();

    }

}