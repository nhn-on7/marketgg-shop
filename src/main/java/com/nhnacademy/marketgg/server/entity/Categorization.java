package com.nhnacademy.marketgg.server.entity;

import com.nhnacademy.marketgg.server.dto.request.CategorizationCreateRequest;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "categorizations")
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Categorization {

    @Id
    @Column(name = "categorization_code")
    private String categorizationCode;

    @Column
    private String name;

    @Column
    private String alias;

    public Categorization(CategorizationCreateRequest categorizationRequest) {
        this.categorizationCode = categorizationRequest.getCategorizationCode();
        this.name = categorizationRequest.getName();
        this.alias = categorizationRequest.getAlias();
    }

}
