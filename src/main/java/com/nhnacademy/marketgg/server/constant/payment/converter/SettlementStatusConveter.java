package com.nhnacademy.marketgg.server.constant.payment.converter;

import com.nhnacademy.marketgg.server.constant.payment.SettlementStatus;
import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class SettlementStatusConveter implements AttributeConverter<SettlementStatus, String> {

    @Override
    public String convertToDatabaseColumn(SettlementStatus attribute) {
        return attribute.name();
    }

    @Override
    public SettlementStatus convertToEntityAttribute(String dbData) {
        return SettlementStatus.valueOf(dbData);
    }

}
