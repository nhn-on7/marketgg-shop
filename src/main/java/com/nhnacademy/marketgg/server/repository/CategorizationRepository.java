package com.nhnacademy.marketgg.server.repository;

import com.nhnacademy.marketgg.server.dto.response.CategorizationRetrieveResponse;
import com.nhnacademy.marketgg.server.entity.Categorization;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CategorizationRepository extends JpaRepository<Categorization, String> {

    @Query("SELECT cz.categorizationCode AS categorizationCode, cz.name AS name FROM Categorization cz")
    List<CategorizationRetrieveResponse> findAllCategorization();

}
