package com.nhnacademy.marketgg.server.mapper;

public interface EntityMapper<V, E> {

    E toEntity(V dto);

    V toDto(E entity);

}
