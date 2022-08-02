package com.nhnacademy.marketgg.server.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 이미지 개체입니다.
 *
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
    @NotNull
    private Long id;

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

    public void setImageSequence(Integer sequence) {
        this.imageSequence = sequence;
    }

}
