package com.nhnacademy.marketgg.server.config;

import java.util.List;
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
    private final List<Step> stepList;

    @Bean
    public Job memberGradeJob() {
        return jobBuilderFactory.get("memberGradeJob")
                                .start(stepList.get(2))
                                .next(stepList.get(0))
                                .next(stepList.get(1))
                                .build();
    }

}
