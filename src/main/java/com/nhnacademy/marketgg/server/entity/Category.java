package com.nhnacademy.marketgg.server.entity;

import com.nhnacademy.marketgg.server.dto.CategoryRegisterRequest;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Table(name = "categories")
@Entity
@NoArgsConstructor
@Getter
public class Category {

    @Id
    // @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long categoryNo;

    @ManyToOne
    @JoinColumn(name = "supercategory_no")
    private Category category;

    @Column
    private String name;

    @Column
    private Integer sequence;

    public Category(CategoryRegisterRequest request, Category category) {
        this.category = category;
        this.name = request.getName();
        this.sequence = request.getSequence();
    }
}
