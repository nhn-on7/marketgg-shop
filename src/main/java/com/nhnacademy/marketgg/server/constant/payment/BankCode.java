package com.nhnacademy.marketgg.server.constant.payment;

import java.util.Arrays;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * 이체할 수 있는 은행 코드표입니다.
 *
 * @author 이제훈
 * @version 1.0
 * @since 1.0
 */
@RequiredArgsConstructor
@Getter
public enum BankCode {

    KYONGNAMBANK("경남", "39", "경남은행", true),
    GWANGJUBANK("광주", "34", "광주은행", true),
    KOOKMIN("국민", "06", "KB국민은행", true),
    IBK("기업", "03", "IBK기업은행", true),
    NONGHYEOP("농협", "11", "NH농협은행", true),
    LOCALNONGHYEOP("단위농협", "12", "단위농협", false),
    DAEGUBANK("대구", "31", "DGB대구은행", true),
    BUSANBANK("부산", "32", "부산은행", true),
    KDBBANK("산업", "02", "KDB산업은행", false),
    SAEMAUL("새마을", "45", "새마을금고", true),
    SANLIM("산림", "64", "산림조합", false),
    SUHYEOP("수협", "07", "Sh수협은행", true),
    SHINHAN("신한", "41", "신한은행", true),
    SHINHYEOP("신협", "48", "신협", false),
    CITI("씨티", "27", "씨티은행", false),
    WOORI("우리", "20", "우리은행", true),
    POST("우체국", "71", "우체국예금보험", true),
    SAVINGBANK("저축", "50", "저축은행중앙회", false),
    JEONBUKBANK("전북", "37", "전북은행", true),
    KAKAOBANK("카카오뱅크", "90", "카카오뱅크", false),
    KBANK("케이뱅크", "89", "케이뱅크", true),
    TOSSBANK("토스뱅크", "92", "토스뱅크", false),
    HANA("하나", "81", "하나은행", true),
    SC("SC제일", "23", "SC제일은행", false),
    HSBC("홍콩상하이", "54", "홍콩상하이은행", false);

    private final String koreanName;
    private final String code;
    private final String bankCompany;
    private final boolean canBeIssuedVirtualAccount;

    /**
     * 문자열로 구성된 가상계좌 발급 은행을 통해 열거형을 추출합니다.
     *
     * @param bankCode - 문자열로 구성된 가상계좌 발급 은행 코드
     * @return 열거형 타입의 가상계좌 발급 은행 코드 {@link BankCode}
     */
    public static BankCode of(String bankCode) {
        return Arrays.stream(BankCode.values())
                     .filter(v -> v.getKoreanName().equals(bankCode))
                     .findAny()
                     .orElseThrow(IllegalArgumentException::new);
    }

}
