package com.nhnacademy.marketgg.server.entity.elastic;

import com.nhnacademy.marketgg.server.entity.Image;
import com.nhnacademy.marketgg.server.entity.Label;
import com.nhnacademy.marketgg.server.entity.Product;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * ES Server 의 "products" 인덱스의 필드의 설정입니다.
 * 에노테이션 @Document 를 통해 인덱스의 이름을 명명해줄 수 있습니다.
 *
 * @version 1.0.0
 */
@Document(indexName = "products")
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class EsProduct {

    @Id
    @Column
    private Long id;

    @Column
    private String productName;

    @Column
    private String categoryCode;

    @Column
    private String labelName;

    @Column
    private String imageAddress;

    @Column
    private Long price;

    // 상품 한줄 설명
    @Column
    private String content;

    public EsProduct(final Product product, final Label label, final Image image) {
        this.id = product.getId();
        this.productName = product.getName();
        this.categoryCode = product.getCategory().getId();
        this.labelName = label.getName();
        this.imageAddress = image.getImageAddress();
        this.price = product.getPrice();
        this.content = product.getContent();
    }

}
