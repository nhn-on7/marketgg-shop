package com.nhnacademy.marketgg.server.entity;

import com.nhnacademy.marketgg.server.dto.request.ProductCreateRequest;
import com.nhnacademy.marketgg.server.dto.request.ProductUpdateRequest;
import java.time.LocalDate;
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

    @Column
    private String description;

    @Column
    private String unit;

    @Column(name = "delivery_type")
    private String deliveryType;

    @Column
    private String origin;

    @Column(name = "package_type")
    private String packageType;

    @Column(name = "expiration_date")
    private LocalDate expirationDate;

    @Column(name = "allergy_info")
    private String allergyInfo;

    @Column
    private String capacity;

    @Column(name = "created_at")
    private LocalDate createdAt;

    @Column(name = "updated_at")
    private LocalDate updatedAt;

    @Column(name = "deleted_at")
    private LocalDate deletedAt;

    public Product(ProductCreateRequest productRequest) {
        this.categoryNo = productRequest.getCategoryNo();
        this.name = productRequest.getName();
        this.content = productRequest.getContent();
        this.totalStock = productRequest.getTotalStock();
        this.price = productRequest.getPrice();
        this.thumbnail = productRequest.getThumbnail();
        this.description = productRequest.getDescription();
        this.unit = productRequest.getUnit();
        this.deliveryType = productRequest.getDeliveryType();
        this.origin = productRequest.getOrigin();
        this.packageType = productRequest.getPackageType();
        this.expirationDate = productRequest.getExpirationDate();
        this.allergyInfo = productRequest.getAllergyInfo();
        this.capacity = productRequest.getCapacity();
        this.createdAt = LocalDate.now();
    }

    public void updateProduct(ProductUpdateRequest productRequest) {
        this.name = productRequest.getName();
        this.content = productRequest.getContent();
        this.totalStock = productRequest.getTotalStock();
        this.price = productRequest.getPrice();
        this.thumbnail = productRequest.getThumbnail();
        this.description = productRequest.getDescription();
        this.unit = productRequest.getUnit();
        this.deliveryType = productRequest.getDeliveryType();
        this.origin = productRequest.getOrigin();
        this.packageType = productRequest.getPackageType();
        this.expirationDate = productRequest.getExpirationDate();
        this.allergyInfo = productRequest.getAllergyInfo();
        this.capacity = productRequest.getCapacity();
        this.updatedAt = LocalDate.now();
    }

    public void deleteProduct() {
        this.deletedAt = LocalDate.now();
    }

}
