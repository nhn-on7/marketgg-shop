package com.nhnacademy.marketgg.server.repository.dib;

import com.nhnacademy.marketgg.server.entity.Dib;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 찜 레포지토리 입니다.
 *
 * @version 1.0.0
 */

public interface DibRepository extends JpaRepository<Dib, Dib.Pk>, DibRepositoryCustom {

}
