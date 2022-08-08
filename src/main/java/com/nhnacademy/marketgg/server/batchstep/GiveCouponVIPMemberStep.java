package com.nhnacademy.marketgg.server.batchstep;

import static com.nhnacademy.marketgg.server.constant.CouponName.VIP;

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
public class GiveCouponVIPMemberStep {

    private final EntityManagerFactory entityManagerFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final CouponRepository couponRepository;

    private static final int CHUNK_SIZE = 5;

    @Bean
    public Step vipGivenCouponMemberStep() {
        return stepBuilderFactory.get("vipGivenCouponMemberStep")
                                 .<Member, GivenCoupon>chunk(CHUNK_SIZE)
                                 .reader(vipMemberReader())
                                 .processor(vipGivenCouponProcessor())
                                 .writer(vipMemberWriter())
                                 .allowStartIfComplete(true)
                                 .build();
    }

    @Bean
    public JpaPagingItemReader<Member> vipMemberReader() {
        return new JpaPagingItemReaderBuilder<Member>()
            .queryString("SELECT m FROM Member m WHERE m.memberGrade = 2")
            .pageSize(CHUNK_SIZE)
            .entityManagerFactory(entityManagerFactory)
            .name("vipMemberReader")
            .build();
    }

    @Bean
    public ItemProcessor<Member, GivenCoupon> vipGivenCouponProcessor() {
        Coupon vipCoupon = couponRepository.findCouponByName(VIP.couponName())
                                           .orElseThrow(CouponNotFoundException::new);
        return member -> new GivenCoupon(vipCoupon, member);
    }

    @Bean
    public JpaItemWriter<GivenCoupon> vipMemberWriter() {
        JpaItemWriter<GivenCoupon> writer = new JpaItemWriter<>();
        writer.setEntityManagerFactory(entityManagerFactory);
        return writer;
    }

}
