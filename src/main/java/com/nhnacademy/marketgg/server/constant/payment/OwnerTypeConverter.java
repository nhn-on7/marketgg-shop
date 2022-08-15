package com.nhnacademy.marketgg.server.constant.payment;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class OwnerTypeConverter implements AttributeConverter<OwnerType, String> {

    @Override
    public String convertToDatabaseColumn(OwnerType attribute) {
        return attribute.getName();
    }

    @Override
    public OwnerType convertToEntityAttribute(String dbData) {
        return OwnerType.of(dbData);
    }
}
