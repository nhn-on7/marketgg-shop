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

@Table(name = "categories")
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Category {

    @Id
    @Column(name = "category_code", nullable = false)
    private String categoryCode;

    @ManyToOne
    @JoinColumn(name = "categorization_code", nullable = false)
    private Categorization categorization;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private Integer sequence;

    public Category(final CategoryCreateRequest categoryCreateRequest, final Categorization categorization) {
        this.categoryCode = categoryCreateRequest.getCategoryCode();
        this.categorization = categorization;
        this.name = categoryCreateRequest.getName();
        this.sequence = categoryCreateRequest.getSequence();
    }

    public void updateCategory(CategoryUpdateRequest categoryRequest, Categorization categorization) {
        this.categorization = categorization;
        this.name = categoryRequest.getName();
        this.sequence = categoryRequest.getSequence();
    }

}
