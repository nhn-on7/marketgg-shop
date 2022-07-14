package com.nhnacademy.marketgg.server.entity;

import com.nhnacademy.marketgg.server.dto.request.CategoryCreateRequest;
import com.nhnacademy.marketgg.server.dto.request.CategoryUpdateRequest;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * 카테고리 Entity 입니다.
 *
 * @version 1.0.0
 */
@Table(name = "categories")
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Category {

    /**
     * 카테고리의 식별코드 입니다.
     *
     * @since 1.0.0
     */
    @Id
    @Column(name = "category_code")
    private String categoryCode;

    /**
     * 카테고리가 속할 카테고리 분류 입니다.
     *
     * @since 1.0.0
     */
    @ManyToOne
    @JoinColumn(name = "categorization_code")
    private Categorization categorization;

    /**
     * 카테고리의 이름입니다.
     *
     * @since 1.0.0
     */
    @Column
    private String name;

    /**
     * 카테고리의 순서위치를 지정받을 순서입니다.
     *
     * @since 1.0.0
     */
    @Column
    private Integer sequence;

    /**
     * 카테고리를 생성하기 위한 생성자입니다.
     *
     * @param categoryCreateRequest 카테고리를 생성하기위한 DTO 입니다.
     * @param categorization 카테고리가 속할 카테고리 분류입니다.
     *
     * @since 1.0.0
     */
    public Category(final CategoryCreateRequest categoryCreateRequest, final Categorization categorization) {
        this.categoryCode = categoryCreateRequest.getCategoryCode();
        this.categorization = categorization;
        this.name = categoryCreateRequest.getName();
        this.sequence = categoryCreateRequest.getSequence();
    }

    /**
     * 카테고리를 수정하기 위한 메소드입니다.
     *
     * @param categoryRequest 카테고리를 수정하기위한 DTO 입니다.
     * @since 1.0.0
     */
    public void updateCategory(CategoryUpdateRequest categoryRequest) {
        this.name = categoryRequest.getName();
        this.sequence = categoryRequest.getSequence();
    }

}
