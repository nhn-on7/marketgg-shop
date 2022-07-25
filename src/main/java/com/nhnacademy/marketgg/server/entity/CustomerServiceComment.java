package com.nhnacademy.marketgg.server.entity;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Table(name = "cs_comment")
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Getter
@Setter
public class CustomerServiceComment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cs_comment_no")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "cs_post_no")
    private CustomerServicePost csPostId;

    @ManyToOne
    @JoinColumn(name = "member_no")
    private Member member;

    @Column
    private String content;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

}
