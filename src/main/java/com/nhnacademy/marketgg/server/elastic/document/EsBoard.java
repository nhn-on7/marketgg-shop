package com.nhnacademy.marketgg.server.elastic.document;

import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.elasticsearch.annotations.Document;

@Document(indexName = "boards")
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class EsBoard {

    @Id
    @Column
    private Long id;

    @Column
    private String title;

    @Column
    private String categoryCode;

    @Column
    private String status;

    @Column
    private String reason;

    @Column
    private LocalDateTime createdAt;

}
