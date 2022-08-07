package com.nhnacademy.marketgg.server.config;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class BatchJobConfig {

    private final JobBuilderFactory jobBuilderFactory;
    private final Step updateMemberGradeStep;
    private final Step giveCouponGVIPMemberStep;
    private final Step giveCouponVIPMemberStep;

    @Bean
    public Job memberGradeJob() {
        return jobBuilderFactory.get("memberGradeJob")
                                .start(updateMemberGradeStep)
                                .next(giveCouponGVIPMemberStep)
                                .next(giveCouponVIPMemberStep)
                                .build();
    }

}
