package com.nhnacademy.marketgg.server.entity;

import com.nhnacademy.marketgg.server.dto.request.category.CategorizationCreateRequest;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 카테고리 분류 개체입니다.
 *
 * @author 공통
 * @version 1.0
 * @since 1.0
 */
@Table(name = "categorizations")
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Categorization {

    @Id
    @Column(name = "categorization_code")
    @NotBlank
    @Size(min = 3, max = 3)
    private String id;

    @Column
    @NotBlank
    @Size(min = 1, max = 20)
    private String name;

    @Column
    @NotBlank
    @Size(min = 1, max = 20)
    private String alias;

    /**
     * 카테고리 분류표를 생성하기 위한 생성자입니다.
     *
     * @param categorizationRequest - 카테고리 분류표를 생성하기 위한 DTO 입니다.
     * @since 1.0.0
     */
    public Categorization(CategorizationCreateRequest categorizationRequest) {
        this.id = categorizationRequest.getCategorizationCode();
        this.name = categorizationRequest.getName();
        this.alias = categorizationRequest.getAlias();
    }

}
