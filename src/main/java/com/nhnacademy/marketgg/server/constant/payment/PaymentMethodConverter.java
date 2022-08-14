package com.nhnacademy.marketgg.server.constant.payment;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

/**
 * 결제 수단에 대해 타입 변환 작업을 처리해주는 클래스입니다.
 *
 * @author 이제훈
 * @version 1.0
 * @since 1.0
 */
@Converter
public class PaymentMethodConverter implements AttributeConverter<PaymentMethod, String> {

    @Override
    public String convertToDatabaseColumn(PaymentMethod attribute) {
        return attribute.getName();
    }

    @Override
    public PaymentMethod convertToEntityAttribute(String dbData) {
        return PaymentMethod.of(dbData);
    }

}
