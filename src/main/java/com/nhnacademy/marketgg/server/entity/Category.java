package com.nhnacademy.marketgg.server.entity;

import com.nhnacademy.marketgg.server.dto.CategoryRequest;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.*;

@Table(name = "Categories")
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Getter
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_no")
    private Long categoryNo;

    @ManyToOne
    @JoinColumn(name = "supercategory_no")
    private Category superCategory;

    @Column
    private String name;

    @Column
    private Integer sequence;

    @Column
    private String code;

    public Category(Category superCategory, CategoryRequest categoryRequest) {
        this.superCategory = superCategory;
        this.name = categoryRequest.getName();
        this.sequence = categoryRequest.getSequence();
        this.code = categoryRequest.getCode();
    }

    public void setSuperCategory(Category superCategory) {
        this.superCategory = superCategory;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSequence(Integer sequence) {
        this.sequence = sequence;
    }

    public void setCode(String code) {
        this.code = code;
    }

}
