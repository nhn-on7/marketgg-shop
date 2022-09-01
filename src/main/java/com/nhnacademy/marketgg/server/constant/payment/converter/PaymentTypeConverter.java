package com.nhnacademy.marketgg.server.constant.payment.converter;

import com.nhnacademy.marketgg.server.constant.payment.PaymentType;
import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

/**
 * 결제 수단에 대한 타입 변환 작업을 처리해주는 클래스입니다.
 *
 * @author 이제훈
 * @version 1.0
 * @since 1.0
 */
@Converter
public class PaymentTypeConverter implements AttributeConverter<PaymentType, String> {

    @Override
    public String convertToDatabaseColumn(PaymentType attribute) {
        return attribute.getType();
    }

    @Override
    public PaymentType convertToEntityAttribute(String type) {
        return PaymentType.of(type);
    }

}
