package com.nhnacademy.marketgg.server.mapper;

import java.util.List;

/**
 * Mapstruct 를 사용하기 위한 Base Mapper 클래스입니다.
 * @param <V> - Dto 객체입니다.
 * @param <E> - Entity 객체입니다.
 *
 * @version 1.0.0
 */
 // MEMO 4: EntityMapper 생성 -> 이거 extend 해서 각 Domain Mapper Interface 생성
public interface EntityMapper<V, E> {

    /**
     * Dto 객체를 Entity 객체로 전환하기 위한 메소드 입니다.
     *
     * @param dto - Entity 객체로 전환할 Dto 객체입니다.
     * @return 전환된 Entity 객체를 반환합니다.
     * @since 1.0.0
     */
    E toEntity(V dto);

    /**
     * Entity 객체를 DTO 객체로 전환하기 위한 메소드 입니다.
     *
     * @param entity - Dto 객체로 전환할 Entity 객체입니다.
     * @return 전환된 Dto 객체를 반환합니다.
     * @since 1.0.0
     */
    V toDto(E entity);

    List<V> toDtoList(List<E> entityList);

    List<E> toEntityList(List<V> dtoList);

}
