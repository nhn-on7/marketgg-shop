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

@Table(name = "funding_products")
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class FundingProduct {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "funding_product_no")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "product_no")
    private Product product;

    @ManyToOne
    @JoinColumn(name = "crowdfunding_no")
    private Crowdfunding crowdfunding;

    @Column
    private Long amount;

}
