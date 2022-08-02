package com.nhnacademy.marketgg.server.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 자원 개체입니다.
 *
 * @version 1.0
 * @since 1.0
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
    @NotNull
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    @JsonFormat(pattern = "yyyy-MM-dd kk:mm:ss")
    @NotNull
    private LocalDateTime updatedAt;

    @Column(name = "deleted_at")
    @JsonFormat(pattern = "yyyy-MM-dd kk:mm:ss")
    private LocalDateTime deletedAt;

    /**
     * 빈 장바구니를 생성해주는 정적 팩토리 메서드입니다.
     *
     * @return 자원 객체
     */
    public static Asset create() {
        Asset asset = new Asset();
        asset.createdAt = LocalDateTime.now();
        asset.updatedAt = LocalDateTime.now();

        return asset;
    }

}
