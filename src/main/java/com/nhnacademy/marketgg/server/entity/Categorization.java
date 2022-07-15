package com.nhnacademy.marketgg.server.entity;

import com.nhnacademy.marketgg.server.dto.request.CategorizationCreateRequest;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 카테고리 분류 Entity 입니다.
 *
 * @version 1.0.0
 */
@Table(name = "categorizations")
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Categorization {

    @Id
    @Column(name = "categorization_code")
    private String categorizationCode;

    @Column
    private String name;

    @Column
    private String alias;

    /**
     * 카테고리 분류표를 생성하기 위한 생성자입니다.
     *
     * @param categorizationRequest - 카테고리 분류표를 생성하기 위한 DTO 입니다.
     * @since 1.0.0
     */
    public Categorization(CategorizationCreateRequest categorizationRequest) {
        this.categorizationCode = categorizationRequest.getCategorizationCode();
        this.name = categorizationRequest.getName();
        this.alias = categorizationRequest.getAlias();
    }

}
