package com.nhnacademy.marketgg.server.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Table(name = "customer_service_post")
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class CustomerServicePost {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cs_post_no")
    private Long csPostNo;

    @ManyToOne
    @JoinColumn(name = "member_no")
    private Member member;

}
