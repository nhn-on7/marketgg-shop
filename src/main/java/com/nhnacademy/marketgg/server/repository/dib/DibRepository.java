package com.nhnacademy.marketgg.server.repository.dib;

import com.nhnacademy.marketgg.server.entity.Dib;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DibRepository extends JpaRepository<Dib, Dib.Pk>, DibRepositoryCustom {

}
