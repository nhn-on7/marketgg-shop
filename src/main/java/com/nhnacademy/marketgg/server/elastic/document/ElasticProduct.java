package com.nhnacademy.marketgg.server.elastic.document;

import com.nhnacademy.marketgg.server.entity.Image;
import com.nhnacademy.marketgg.server.entity.Label;
import com.nhnacademy.marketgg.server.entity.Product;
import javax.persistence.Id;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;

@Document(indexName = "products")
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
public class ElasticProduct {

    @Id
    @Field
    private Long id;

    @Field
    private String categoryCode;

    @Field
    private String productName;

    @Field
    private String content;

    @Field
    private String description;

    @Field
    private String labelName;

    @Field
    private String imageAddress;

    @Field
    private Long price;

    @Field
    private Long amount;

    /**
     * 엘라스틱 서치의 상품을 생성하는 생성자입니다.
     *
     * @param product - 등록할 상품의 정보를 담고있습니다.
     * @param label   - 등록할 라벨의 정보를 담고있습니다.
     * @param image   - 등록할 이미지의 정보를 담고있습니다.
     * @author 박세완
     * @since 1.0.0
     */
    public ElasticProduct(final Product product, final Label label, final Image image) {
        this.id = product.getId();
        this.categoryCode = product.getCategory().getId();
        this.productName = product.getName();
        this.content = product.getContent();
        this.description = product.getDescription();
        this.labelName = label.getName();
        this.imageAddress = image.getImageAddress();
        this.price = product.getPrice();
        this.amount = product.getTotalStock();
    }

}
