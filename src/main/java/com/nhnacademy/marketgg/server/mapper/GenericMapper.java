package com.nhnacademy.marketgg.server.mapper;

public interface GenericMapper<V, E> {
    V toVO(E entity);

    E toEntity(V vo);
}
