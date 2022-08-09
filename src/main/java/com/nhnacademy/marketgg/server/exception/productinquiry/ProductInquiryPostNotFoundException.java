package com.nhnacademy.marketgg.server.exception.productinquiry;

import com.nhnacademy.marketgg.server.exception.NotFoundException;

/**
 * 상품문의를 찾을 수 없을 때 예외처리입니다.
 *
 * @author 공통
 * @version 1.0.0
 */
public class ProductInquiryPostNotFoundException extends NotFoundException {

    private static final String ERROR = "상품 문의를 찾을 수 없습니다.";

    /**
     * 예외처리 시, 지정한 메세지를 보냅니다.
     *
     * @since 1.0.0
     */
    public ProductInquiryPostNotFoundException() {
        super(ERROR);
    }

    /**
     * 상품 문의 작성한 회원의 정보를 찾을 수 없을 때 예외처리입니다.
     *
     * @version 1.0.0
     */
    public static class MemberWriteInquiryNotFoundException extends IllegalArgumentException {

        private static final String ERROR = "상품 문의 작성한 회원의 정보를 찾을 수 없습니다.";

        public MemberWriteInquiryNotFoundException() {
            super(ERROR);
        }
    }

    /**
     * 생성할 상품 문의에 상품 정보를 찾을 수 없을 때 예외처리입니다.
     *
     * @version 1.0.0
     */
    public static class ProductAtInquiryNotFoundException extends IllegalArgumentException {

        private static final String ERROR = "생성할 상품 문의에 상품 정보를 찾을 수 없습니다.";

        public ProductAtInquiryNotFoundException() {
            super(ERROR);
        }
    }

}
