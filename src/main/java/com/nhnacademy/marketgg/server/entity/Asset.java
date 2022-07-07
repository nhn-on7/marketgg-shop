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

@Table(name = "assets")
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Asset {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "asset_no")
    private Long assetNo;

    @Column(name = "is_uploaded")
    private boolean isUploaded;

    @Column(name = "is_deleted")
    private boolean isDeleted;

}
