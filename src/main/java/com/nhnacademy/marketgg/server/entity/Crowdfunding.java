package com.nhnacademy.marketgg.server.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Table(name = "crowdfunding")
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Crowdfunding {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "crowdfunding_no")
    private Long crowdfundingNo;

    @Column(name = "current_amount")
    private Long currentAmount;

    @Column(name = "target_amount")
    private Long targetAmount;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column
    private LocalDateTime deadline;

}
