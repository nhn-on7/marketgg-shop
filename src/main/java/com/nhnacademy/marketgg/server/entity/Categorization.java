package com.nhnacademy.marketgg.server.entity;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "categorizations")
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Getter
public class Categorization {

    @Id
    @Column(name = "categorization_code")
    private String categorizationCode;

    @Column(name = "categorization_name")
    private String categorizationName;

    @Column(name = "categorization_mean")
    private String categorizationMean;

}
