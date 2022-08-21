package com.nhnacademy.marketgg.server.constant.payment.converter;

import com.nhnacademy.marketgg.server.constant.payment.BankCode;
import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class BankCodeConverter implements AttributeConverter<BankCode, String> {

    @Override
    public String convertToDatabaseColumn(BankCode attribute) {
        return attribute.getKoreanName();
    }

    @Override
    public BankCode convertToEntityAttribute(String dbData) {
        return BankCode.of(dbData);
    }

}
