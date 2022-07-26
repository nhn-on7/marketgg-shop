package com.nhnacademy.marketgg.server.elastic.document;

import javax.persistence.Entity;
import javax.persistence.Id;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;

@Document(indexName = "products")
@Entity
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class EsProduct {

    @Id
    @Field
    private Long id;

    @Field
    private String productName;

    @Field
    private String categoryCode;

    @Field
    private String labelName;

    @Field
    private String imageAddress;

    @Field
    private String price;

    @Field
    private String amount;

    @Field
    private String content;

    @Field
    private String description;

}
