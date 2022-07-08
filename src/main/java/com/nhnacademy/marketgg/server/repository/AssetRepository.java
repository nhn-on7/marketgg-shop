package com.nhnacademy.marketgg.server.repository;

import com.nhnacademy.marketgg.server.entity.Asset;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AssetRepository extends JpaRepository<Asset, Long> {
}
