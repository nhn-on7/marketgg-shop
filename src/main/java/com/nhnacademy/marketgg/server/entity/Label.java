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

/**
 * 라벨 Entity 입니다.
 *
 * @version 1.0.0
 */
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

    /**
     * 라벨을 생성하기 위한 생성자입니다.
     *
     * @param labelCreateRequest - 라벨을 생성하기 위한 DTO 입니다.
     * @since 1.0.0
     */
    public Label(LabelCreateRequest labelCreateRequest) {
        this.name = labelCreateRequest.getName();
    }

}
