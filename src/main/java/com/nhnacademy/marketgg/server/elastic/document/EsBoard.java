package com.nhnacademy.marketgg.server.elastic.document;

import java.time.LocalDateTime;
import javax.persistence.Entity;
import javax.persistence.Id;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;

@Document(indexName = "boards")
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class EsBoard {

    @Id
    @Field
    private Long id;

    @Field
    private String title;

    @Field
    private String categoryCode;

    @Field
    private String status;

    @Field
    private String reason;

    @Field
    private LocalDateTime createdAt;

}
