package com.nhnacademy.marketgg.server.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.nhnacademy.marketgg.server.dto.request.LabelCreateRequest;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Table(name = "labels")
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Label {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "label_no")
    private Long labelNo;

    @Column
    private String name;

    public Label(LabelCreateRequest labelCreateRequest) {
        this.name = labelCreateRequest.getName();
    }

}
