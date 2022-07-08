package com.nhnacademy.marketgg.server.entity;

import com.nhnacademy.marketgg.server.dto.CategoryUpdateRequest;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
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
@AllArgsConstructor
@Getter
public class Category {

    @Id
    @Column(name = "category_code")
    private String categoryCode;

    @ManyToOne
    @JoinColumn(name = "categorization_code")
    private Categorization categorization;

    @Column
    private String name;

    @Column
    private Integer sequence;

    public void updateCategory(CategoryUpdateRequest categoryRequest, Categorization categorization) {
        this.categorization = categorization;
        this.name = categoryRequest.getName();
        this.sequence = categoryRequest.getSequence();
    }

}
