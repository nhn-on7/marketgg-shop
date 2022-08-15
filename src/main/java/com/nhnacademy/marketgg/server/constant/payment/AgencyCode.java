package com.nhnacademy.marketgg.server.constant.payment;

import java.util.Arrays;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * 기관 코드입니다.
 *
 * @author 이제훈
 * @version 1.0
 * @since 1.0
 */
@RequiredArgsConstructor
@Getter
public enum AgencyCode {

    KOOKMIN("국민", "11", "KB국민카드"),
    KAKAOBANK("카카오뱅크", "15", "카카오뱅크"),
    HANA("하나", "21", "하나카드"),
    KDBBANK("산업", "30", "KDB산업은행"),
    BC("비씨", "31", "비씨카드"),
    WOORI("우리", "33", "우리카드"),
    SUHYEOP("수협", "34", "Sh수협은행"),
    JEONBUKBANK("전북", "35", "전북은행"),
    CITI("씨티", "36", "씨티카드"),
    POST("우체국", "37", "우체국예금보험"),
    SAEMAUL("새마을", "38", "새마을금고"),
    SAVINGBANK("저축", "39", "저축은행중앙회"),
    SHINHAN("신한", "41", "신한카드"),
    JEJUBANK("제주", "42", "제주은행"),
    GWANGJUBANK("광주", "46", "광주은행"),
    SAMSUNG("삼성", "51", "삼성카드"),
    SHINHYEOP("신협", "62", "신협"),
    HYUNDAI("현대", "61", "현대카드"),
    LOTTE("롯데", "71", "롯데카드"),
    NONGHYEOP("농협", "91", "NH농협카드"),
    KBANK("케이뱅크", "3A", "카카오뱅크"),
    UNIONPAY("유니온페이", "3C", "유니온페이"),
    MASTER("마스터", "4M", "마스터카드"),
    JCB("제이씨비", "4J", "JCB"),
    VISA("비자", "4V", "VISA"),
    DINERS("다이너스", "6D", "다이너스 클럽");

    private final String name;
    private final String code;
    private final String cardCompany;

    /**
     * 문자열로 구성된 기관 종류 구분을 통해 열거형을 추출합니다.
     *
     * @param companyCode - 문자열로 구성된 기관 코드
     * @return 열거형 타입의 기관 코드 {@link CardType}
     */
    public static AgencyCode of(String companyCode) {
        return Arrays.stream(AgencyCode.values())
                     .filter(v -> v.getName().equals(companyCode))
                     .findAny()
                     .orElseThrow(IllegalArgumentException::new);
    }

}
