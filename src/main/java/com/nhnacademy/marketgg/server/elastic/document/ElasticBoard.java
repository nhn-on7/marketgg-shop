package com.nhnacademy.marketgg.server.elastic.document;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.nhnacademy.marketgg.server.entity.CustomerServicePost;
import java.time.LocalDateTime;
import javax.persistence.Id;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;

@Document(indexName = "boards")
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
public class ElasticBoard {

    @Id
    @Field
    private Long id;

    @Field
    private String categoryCode;

    @Field
    private String title;

    @Field
    private String reason;

    @Field
    private String status;

    @Field
    @JsonFormat(pattern = "yyyy-MM-dd kk:mm:ss")
    private LocalDateTime createdAt;

    public ElasticBoard(final CustomerServicePost servicePost) {
        this.id = servicePost.getId();
        this.categoryCode = servicePost.getCategory().getId();
        this.title = servicePost.getTitle();
        this.reason = servicePost.getReason();
        this.status = servicePost.getStatus();
        this.createdAt = servicePost.getCreatedAt();
    }

}
