package com.nhnacademy.marketgg.server.repository;

import com.nhnacademy.marketgg.server.entity.Crowdfunding;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CrowdfundingRepository extends JpaRepository<Crowdfunding, Long> {

}
