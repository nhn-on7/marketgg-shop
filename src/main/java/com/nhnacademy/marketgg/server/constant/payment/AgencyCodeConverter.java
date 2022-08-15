package com.nhnacademy.marketgg.server.constant.payment;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

/**
 * 기관 코드에 대한 타입 변환 작업을 처리해주는 클래스입니다.
 *
 * @author 이제훈
 * @version 1.0
 * @since 1.0
 */
@Converter
public class AgencyCodeConverter implements AttributeConverter<AgencyCode, String> {

    @Override
    public String convertToDatabaseColumn(AgencyCode attribute) {
        return attribute.getCardCompany();
    }

    @Override
    public AgencyCode convertToEntityAttribute(String dbData) {
        return AgencyCode.of(dbData);
    }

}
