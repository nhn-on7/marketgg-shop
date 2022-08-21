package com.nhnacademy.marketgg.server.constant.payment.converter;

import com.nhnacademy.marketgg.server.constant.payment.AccountType;
import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

/**
 * 가상계좌 타입에 대한 변환 작업을 처리해주는 클래스입니다.
 *
 * @author 이제훈
 * @version 1.0
 * @since 1.0
 */
@Converter(autoApply = true)
public class AccountTypeConverter implements AttributeConverter<AccountType, String> {

    @Override
    public String convertToDatabaseColumn(AccountType attribute) {
        return attribute.getName();
    }

    @Override
    public AccountType convertToEntityAttribute(String dbData) {
        return AccountType.of(dbData);
    }

}
