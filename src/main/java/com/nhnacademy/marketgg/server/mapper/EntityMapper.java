package com.nhnacademy.marketgg.server.mapper;

// MEMO 4: EntityMapper 생성 -> 이거 extend 해서 각 Domain Mapper Interface 생성
public interface EntityMapper<V, E> {

    E toEntity(V dto);

    V toDto(E entity);

}
