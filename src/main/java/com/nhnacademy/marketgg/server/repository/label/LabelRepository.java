package com.nhnacademy.marketgg.server.repository.label;

import com.nhnacademy.marketgg.server.entity.Label;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 라벨 레포지토리 입니다.
 *
 * @author 박세완
 * @version 1.0.0
 */
public interface LabelRepository extends JpaRepository<Label, Long>, LabelRepositoryCustom {

}
