package com.nhnacademy.marketgg.server.repository;

import com.nhnacademy.marketgg.server.entity.DeliveryAddress;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeliveryAddressRepository extends JpaRepository<DeliveryAddress, DeliveryAddress.Pk> {

}
