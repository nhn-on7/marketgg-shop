package com.nhnacademy.marketgg.server.entity;

import java.util.Objects;
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
import java.time.LocalDate;
import java.time.LocalDateTime;

@Table
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_no")
    private Long memberNo;

    @ManyToOne
    @JoinColumn(name = "member_grade_no")
    private MemberGrade memberGrade;

    @Column
    private String email;

    @Column
    private Character Gender;

    @Column(name = "birth_date")
    private LocalDate birthDate;

    @Column(name = "ggpass_updated_at")
    private LocalDateTime ggpassUpdatedAt;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    public void passJoin() {
        this.ggpassUpdatedAt = (LocalDateTime.now()).plusMonths(1);
    }

}
