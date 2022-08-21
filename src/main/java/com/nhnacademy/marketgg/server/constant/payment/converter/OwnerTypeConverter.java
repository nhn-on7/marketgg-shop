package com.nhnacademy.marketgg.server.constant.payment.converter;

import com.nhnacademy.marketgg.server.constant.payment.OwnerType;
import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

/**
 * 소유자에 대한 타입 변환 작업을 처리해주는 클래스입니다.
 *
 * @author 이제훈
 * @version 1.0
 * @since 1.0
 */
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
