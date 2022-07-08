package com.nhnacademy.marketgg.server.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Table(name = "assets")
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Asset {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "asset_no")
    private Long assetNo;

    @Column(name = "created_at")
    private LocalDate createAt;

    @Column(name = "updated_at")
    private LocalDate updateAt;

    @Column(name = "deleted_at")
    private LocalDate deletedAt;

    public static Asset create() {
        Asset asset = new Asset();
        asset.createAt = LocalDate.now();
        asset.updateAt = LocalDate.now();
        return asset;
    }

}
