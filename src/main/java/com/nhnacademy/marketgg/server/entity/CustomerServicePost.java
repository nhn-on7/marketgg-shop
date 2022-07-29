package com.nhnacademy.marketgg.server.entity;

import com.nhnacademy.marketgg.server.constant.CustomerServicePostStatus;
import com.nhnacademy.marketgg.server.dto.request.PostRequest;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Table(name = "cs_posts")
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class CustomerServicePost {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cs_post_no")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "member_no")
    private Member member;

    @ManyToOne
    @JoinColumn(name = "category_code")
    private Category category;

    @Column
    private String content;

    @Column
    private String title;

    @Column
    private String reason;

    @Column
    private String status;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    public CustomerServicePost(Member member, Category category, PostRequest postRequest) {
        this.member = member;
        this.category = category;
        this.content = postRequest.getContent();
        this.title = postRequest.getTitle();
        this.reason = postRequest.getReason();
        this.status = CustomerServicePostStatus.UNANSWERED.status();
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

}
