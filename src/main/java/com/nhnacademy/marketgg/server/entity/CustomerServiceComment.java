package com.nhnacademy.marketgg.server.entity;

import java.time.LocalDateTime;
import com.nhnacademy.marketgg.server.dto.request.CommentRequest;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Table(name = "cs_comments")
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
public class CustomerServiceComment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cs_comment_no")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "cs_post_no")
    private CustomerServicePost customerServicePost;

    @ManyToOne
    @JoinColumn(name = "member_no")
    private Member member;

    @Column
    private String content;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    public CustomerServiceComment(CustomerServicePost csPost, Member member, CommentRequest commentRequest) {
        this.customerServicePost = csPost;
        this.member = member;
        this.content = commentRequest.getContent();
        this.createdAt = LocalDateTime.now();
    }

}
