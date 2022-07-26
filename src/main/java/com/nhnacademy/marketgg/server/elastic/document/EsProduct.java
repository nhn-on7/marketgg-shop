package com.nhnacademy.marketgg.server.elastic.document;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.elasticsearch.annotations.Document;

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
    private String price;

    @Column
    private String amount;

    @Column
    private String content;

    @Column
    private String description;

}
