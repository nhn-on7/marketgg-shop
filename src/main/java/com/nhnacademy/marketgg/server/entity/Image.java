package com.nhnacademy.marketgg.server.entity;


import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Table(name = "images")
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "image_no")
    private Long imageNo;

    @ManyToOne
    @JoinColumn(name = "asset_no")
    private Asset asset;

    @Column(name = "image_address")
    private String imageAddress;

    @Column(name = "image_sequence")
    private Integer imageSequence;

    public Image(Asset asset, String imageAddress) {
        this.asset = asset;
        this.imageAddress = imageAddress;
    }

}
