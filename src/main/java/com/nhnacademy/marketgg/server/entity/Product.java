package com.nhnacademy.marketgg.server.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.nhnacademy.marketgg.server.dto.request.ProductCreateRequest;
import com.nhnacademy.marketgg.server.dto.request.ProductUpdateRequest;
import java.time.LocalDate;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
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
    @Column(name = "product_no", nullable = false)
    private Long productNo;

    @OneToOne
    @JoinColumn(name = "asset_no", nullable = false)
    private Asset asset;

    @ManyToOne
    @JoinColumn(name = "category_code", nullable = false)
    private Category category;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String content;

    @Column(name = "total_stock", nullable = false)
    private Long totalStock;

    @Column(nullable = false)
    private Long price;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private String unit;

    @Column(name = "delivery_type", nullable = false)
    private String deliveryType;

    @Column(nullable = false)
    private String origin;

    @Column(name = "package_type", nullable = false)
    private String packageType;

    @Column(name = "expiration_date", nullable = false)
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate expirationDate;

    @Column(name = "allergy_info", nullable = false)
    private String allergyInfo;

    @Column(nullable = false)
    private String capacity;

    @Column(name = "created_at", nullable = false)
    @JsonFormat(pattern = "yyyy-MM-dd kk:mm:ss")
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    @JsonFormat(pattern = "yyyy-MM-dd kk:mm:ss")
    private LocalDateTime updatedAt;

    @Column(name = "deleted_at")
    @JsonFormat(pattern = "yyyy-MM-dd kk:mm:ss")
    private LocalDateTime deletedAt;

    public Product(ProductCreateRequest productRequest, Asset asset, Category category) {
        this.asset = asset;
        this.category = category;
        this.name = productRequest.getName();
        this.content = productRequest.getContent();
        this.totalStock = productRequest.getTotalStock();
        this.price = productRequest.getPrice();
        this.description = productRequest.getDescription();
        this.unit = productRequest.getUnit();
        this.deliveryType = productRequest.getDeliveryType();
        this.origin = productRequest.getOrigin();
        this.packageType = productRequest.getPackageType();
        this.expirationDate = productRequest.getExpirationDate();
        this.allergyInfo = productRequest.getAllergyInfo();
        this.capacity = productRequest.getCapacity();
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    public void updateProduct(ProductUpdateRequest productRequest, Asset asset, Category category) {
        this.asset = asset;
        this.category = category;
        this.name = productRequest.getName();
        this.content = productRequest.getContent();
        this.totalStock = productRequest.getTotalStock();
        this.price = productRequest.getPrice();
        this.description = productRequest.getDescription();
        this.unit = productRequest.getUnit();
        this.deliveryType = productRequest.getDeliveryType();
        this.origin = productRequest.getOrigin();
        this.packageType = productRequest.getPackageType();
        this.expirationDate = productRequest.getExpirationDate();
        this.allergyInfo = productRequest.getAllergyInfo();
        this.capacity = productRequest.getCapacity();
        this.updatedAt = LocalDateTime.now();
    }

    public void deleteProduct() {
        this.deletedAt = LocalDateTime.now();
    }

}
