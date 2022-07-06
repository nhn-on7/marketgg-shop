package com.nhnacademy.marketgg.server.entity;

import com.nhnacademy.marketgg.server.dto.request.ProductCreateRequest;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Table(name = "products")
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productNo;

    @Column(name = "category_no")
    private Long categoryNo;

    @Column
    private String name;

    @Column
    private String content;

    @Column(name = "total_stock")
    private Long totalStock;

    @Column
    private Long price;

    @Column
    private String thumbnail;

    public Product(ProductCreateRequest productRequest) {
        this.categoryNo = productRequest.getCategoryNo();
        this.name = productRequest.getName();
        this.content = productRequest.getContent();
        this.totalStock = productRequest.getTotalStock();
        this.price = productRequest.getPrice();
        this.thumbnail = productRequest.getThumbnail();
    }

}
