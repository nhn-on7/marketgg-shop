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
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

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

    @Id
    @Column(name = "category_code")
    @NotBlank
    @Size(max = 6)
    private String id;

    @ManyToOne
    @JoinColumn(name = "categorization_code")
    @Valid
    private Categorization categorization;

    @Column
    @NotBlank
    @Size(max = 20)
    private String name;

    @Column
    @NotNull
    private Integer sequence;

    /**
     * 카테고리를 생성하기 위한 생성자입니다.
     *
     * @param categoryCreateRequest - 카테고리를 생성하기위한 DTO 입니다.
     * @param categorization        - 카테고리가 속할 카테고리 분류입니다.
     * @since 1.0.0
     */
    public Category(final CategoryCreateRequest categoryCreateRequest, final Categorization categorization) {
        this.id = categoryCreateRequest.getCategoryCode();
        this.categorization = categorization;
        this.name = categoryCreateRequest.getName();
        this.sequence = categoryCreateRequest.getSequence();
    }

    /**
     * 카테고리를 수정하기 위한 메소드입니다.
     *
     * @param categoryRequest - 카테고리를 수정하기위한 DTO 입니다.
     * @since 1.0.0
     */
    public void updateCategory(CategoryUpdateRequest categoryRequest) {
        this.name = categoryRequest.getName();
        this.sequence = categoryRequest.getSequence();
    }

}
