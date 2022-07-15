package com.nhnacademy.marketgg.server.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 자원 엔티티입니다.
 *
 * @version 1.0.0
 */
@Table(name = "assets")
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Asset {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "asset_no")
    private Long id;

    @Column(name = "created_at")
    @JsonFormat(pattern = "yyyy-MM-dd kk:mm:ss")
    private LocalDateTime createAt;

    @Column(name = "updated_at")
    @JsonFormat(pattern = "yyyy-MM-dd kk:mm:ss")
    private LocalDateTime updateAt;

    @Column(name = "deleted_at")
    @JsonFormat(pattern = "yyyy-MM-dd kk:mm:ss")
    private LocalDateTime deletedAt;

    public static Asset create() {
        Asset asset = new Asset();
        asset.createAt = LocalDateTime.now();
        asset.updateAt = LocalDateTime.now();
        return asset;
    }

}
