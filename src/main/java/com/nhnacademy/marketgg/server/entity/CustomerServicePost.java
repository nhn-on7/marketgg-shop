package com.nhnacademy.marketgg.server.entity;

import com.nhnacademy.marketgg.server.constant.CustomerServicePostStatus;
import com.nhnacademy.marketgg.server.dto.request.PostRequest;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 고객센터 게시글 개체입니다.
 */
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

    /**
     * 고객센터 게시글 생성자입니다.
     *
     * @param member      - 게시글 작성 회원
     * @param category    - 게시글이 속한 카테고리
     * @param postRequest - 게시글 요청 객체
     */
    public CustomerServicePost(final Member member, final Category category, final PostRequest postRequest) {
        this.member = member;
        this.category = category;
        this.content = postRequest.getContent();
        this.title = postRequest.getTitle();
        this.reason = postRequest.getReason();
        this.status = CustomerServicePostStatus.UNANSWERED.status();
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    public void updatePost(final PostRequest postRequest) {
        this.title = postRequest.getTitle();
        this.content = postRequest.getContent();
        this.reason = postRequest.getReason();
        this.updatedAt = LocalDateTime.now();
    }

    public void updatePostStatus(final String status) {
        this.status = status;
        this.updatedAt = LocalDateTime.now();
    }

}
