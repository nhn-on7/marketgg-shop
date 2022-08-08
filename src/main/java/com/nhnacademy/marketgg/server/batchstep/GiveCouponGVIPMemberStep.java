package com.nhnacademy.marketgg.server.batchstep;

import static com.nhnacademy.marketgg.server.constant.CouponName.GVIP;

import com.nhnacademy.marketgg.server.entity.Coupon;
import com.nhnacademy.marketgg.server.entity.GivenCoupon;
import com.nhnacademy.marketgg.server.entity.Member;
import com.nhnacademy.marketgg.server.exception.coupon.CouponNotFoundException;
import com.nhnacademy.marketgg.server.repository.coupon.CouponRepository;
import javax.persistence.EntityManagerFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.batch.item.database.JpaPagingItemReader;
import org.springframework.batch.item.database.builder.JpaPagingItemReaderBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
@RequiredArgsConstructor
public class GiveCouponGVIPMemberStep {

    private final EntityManagerFactory entityManagerFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final CouponRepository couponRepository;

    private static final int CHUNK_SIZE = 5;

    @Bean
    public Step gVipGivenCouponMemberStep() {
        return stepBuilderFactory.get("gVipGivenCouponMemberStep")
                                 .<Member, GivenCoupon>chunk(CHUNK_SIZE)
                                 .reader(gVIPMemberReader())
                                 .processor(gVipGivenCouponProcessor())
                                 .writer(gVIPMemberWriter())
                                 .allowStartIfComplete(true)
                                 .build();
    }

    @Bean
    public JpaPagingItemReader<Member> gVIPMemberReader() {
        return new JpaPagingItemReaderBuilder<Member>()
            .queryString("SELECT m FROM Member m WHERE m.memberGrade = 1")
            .pageSize(CHUNK_SIZE)
            .entityManagerFactory(entityManagerFactory)
            .name("gVIPMemberReader")
            .build();
    }

    @Bean
    public ItemProcessor<Member, GivenCoupon> gVipGivenCouponProcessor() {
        Coupon gVipCoupon = couponRepository.findCouponByName(GVIP.couponName())
                                            .orElseThrow(CouponNotFoundException::new);
        return member -> new GivenCoupon(gVipCoupon, member);
    }


    @Bean
    public JpaItemWriter<GivenCoupon> gVIPMemberWriter() {
        JpaItemWriter<GivenCoupon> writer = new JpaItemWriter<>();
        writer.setEntityManagerFactory(entityManagerFactory);
        return writer;
    }

}
