package com.nhnacademy.marketgg.server.mapper;

import com.nhnacademy.marketgg.server.dto.request.CouponDto;
import com.nhnacademy.marketgg.server.entity.Coupon;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

// MEMO 5: spring 에 의존성 주입하여 컴포넌트 사용
@Mapper(componentModel = "spring")
public interface CouponMapper extends EntityMapper<CouponDto, Coupon> {

    //MEMO 8: Update Entity -> 변경된 필드 값을 가진 Dto 를 Entity 에 업데이트하는 메소드
    // NullValuePropertyMappingStrategy.IGNORE -> Dto 의 null 값 체크해서 변경된 값만 Entity 에 반영
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateCouponFromCouponDto(CouponDto couponDto, @MappingTarget Coupon coupon);

}
