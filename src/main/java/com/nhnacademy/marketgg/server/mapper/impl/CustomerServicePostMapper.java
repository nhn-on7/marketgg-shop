package com.nhnacademy.marketgg.server.mapper.impl;

import com.nhnacademy.marketgg.server.dto.response.CustomerServicePostRetrieveResponse;
import com.nhnacademy.marketgg.server.entity.CustomerServicePost;
import com.nhnacademy.marketgg.server.mapper.EntityMapper;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface CustomerServicePostMapper extends EntityMapper<CustomerServicePostRetrieveResponse, CustomerServicePost> {

    // CustomerServicePostDto toDto(CustomerServicePost entity);
    //
    // CustomerServicePost toEntity(CustomerServicePostDto dto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateCustomerServicePostFromCustomerServicePostDto(CustomerServicePostRetrieveResponse customerServicePostRetrieveResponse,
                                                             @MappingTarget CustomerServicePost customerServicePost);

}
