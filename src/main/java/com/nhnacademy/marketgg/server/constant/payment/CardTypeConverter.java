package com.nhnacademy.marketgg.server.constant.payment;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

/**
 * 카드 종류에 대한 타입 변환 작업을 처리해주는 클래스입니다.
 *
 * @author 이제훈
 * @version 1.0
 * @since 1.0
 */
@Converter
public class CardTypeConverter implements AttributeConverter<CardType, String> {

    @Override
    public String convertToDatabaseColumn(CardType attribute) {
        return attribute.getName();
    }

    @Override
    public CardType convertToEntityAttribute(String dbData) {
        return CardType.of(dbData);
    }

}
