package com.nhnacademy.marketgg.server.mapper.impl;

import com.nhnacademy.marketgg.server.dto.response.CustomerServiceCommentDto;
import com.nhnacademy.marketgg.server.entity.CustomerServiceComment;
import com.nhnacademy.marketgg.server.mapper.EntityMapper;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface CustomerServiceCommentMapper extends EntityMapper<CustomerServiceCommentDto, CustomerServiceComment> {

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateCustomerServicePostFromCustomerServicePostDto(CustomerServiceCommentDto customerServiceCommentDto,
                                                             @MappingTarget CustomerServiceComment customerServiceComment);

}
