package com.nhnacademy.marketgg.server.repository;

import com.nhnacademy.marketgg.server.dto.LabelDto;
import com.nhnacademy.marketgg.server.entity.Label;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface LabelRepository extends JpaRepository<Label, Long> {

    @Query("SELECT l.name AS name FROM Label l")
    List<LabelDto> findAllLabels();
}
