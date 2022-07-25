package com.nhnacademy.marketgg.server.mapper.impl;

import com.nhnacademy.marketgg.server.dto.response.CustomerServicePostDto;
import com.nhnacademy.marketgg.server.entity.CustomerServicePost;
import com.nhnacademy.marketgg.server.mapper.EntityMapper;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface CustomerServicePostMapper extends EntityMapper<CustomerServicePostDto, CustomerServicePost> {

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateCustomerServicePostFromCustomerServicePostDto(CustomerServicePostDto customerServicePostDto,
                                                             @MappingTarget CustomerServicePost customerServicePost);

}
