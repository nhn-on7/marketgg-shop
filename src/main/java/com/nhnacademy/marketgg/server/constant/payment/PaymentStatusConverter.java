package com.nhnacademy.marketgg.server.constant.payment;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class PaymentStatusConverter implements AttributeConverter<PaymentStatus, String> {

    @Override
    public String convertToDatabaseColumn(PaymentStatus attribute) {
        return attribute.getName();
    }

    @Override
    public PaymentStatus convertToEntityAttribute(String dbData) {
        return PaymentStatus.of(dbData);
    }

}
