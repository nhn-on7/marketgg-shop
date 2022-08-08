package com.nhnacademy.marketgg.server.config;

import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@EnableBatchProcessing
@Configuration
@RequiredArgsConstructor
public class BatchJobConfig {

    private final JobBuilderFactory jobBuilderFactory;
    private final Step memberGradeUpdateStep;
    private final Step gVipGivenCouponMemberStep;
    private final Step vipGivenCouponMemberStep;

    @Bean
    public Job memberGradeJob() {
        return jobBuilderFactory.get(UUID.randomUUID().toString())
                                .start(memberGradeUpdateStep)
                                .next(gVipGivenCouponMemberStep)
                                .next(vipGivenCouponMemberStep)
                                .build();
    }

}
