package com.nhnacademy.marketgg.server.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 이미지 개체입니다.
 *
 * @author 공통
 * @version 1.0
 * @since 1.0
 */
@Table(name = "images")
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "image_no")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "asset_no")
    private Asset asset;

    @Column(name = "image_address")
    private String imageAddress;

    @Column
    private String name;

    @Column
    private String type;

    @Column
    private Long length;

    @Column
    private String classification;

    @Builder
    private Image(Asset asset, String imageAddress, String name, String type, Long length,
                  String classification) {
        this.asset = asset;
        this.imageAddress = imageAddress;
        this.name = name;
        this.type = type;
        this.length = length;
        this.classification = classification;
    }

}
