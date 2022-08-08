package com.nhnacademy.marketgg.server.scheduler;


import static com.nhnacademy.marketgg.server.constant.CouponName.BIRTHDAY;

import com.nhnacademy.marketgg.server.config.BatchJobConfig;
import com.nhnacademy.marketgg.server.entity.Coupon;
import com.nhnacademy.marketgg.server.entity.GivenCoupon;
import com.nhnacademy.marketgg.server.entity.Member;
import com.nhnacademy.marketgg.server.exception.coupon.CouponNotFoundException;
import com.nhnacademy.marketgg.server.repository.coupon.CouponRepository;
import com.nhnacademy.marketgg.server.repository.givencoupon.GivenCouponRepository;
import com.nhnacademy.marketgg.server.repository.member.MemberRepository;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class CouponIssuanceScheduler {

    private final JobLauncher jobLauncher;
    private final BatchJobConfig jobConfig;

    private final CouponRepository couponRepository;
    private final MemberRepository memberRepository;
    private final GivenCouponRepository givenCouponRepository;

    @Async
    @Scheduled(cron = "@daily", zone = "Asia/Seoul")
    public void scheduleBirthdayCoupon() {
        log.info("생일 쿠폰 스케줄러 시작 시간: {}", LocalDateTime.now());

        Coupon birthdayCoupon = couponRepository.findCouponByName(BIRTHDAY.couponName())
                                                .orElseThrow(CouponNotFoundException::new);

        String todayDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("MM-dd"));
        List<Member> members = memberRepository.findAllMembersByBirthday(todayDate);

        for (Member member : members) {
            log.info("오늘 생일인 회원: {}", member);
            GivenCoupon givenCoupon = new GivenCoupon(birthdayCoupon, member);
            givenCouponRepository.save(givenCoupon);
        }
        log.info("스케줄러 끝 시간: {}", LocalDateTime.now());
    }

    @Async
    @Scheduled(cron = "30 59 15 * * *", zone = "Asia/Seoul")
    public void scheduleMemberGradeCoupon() {
        log.info("등급 쿠폰 스케줄러 시작 시간: {}", LocalDateTime.now());

        Job job = jobConfig.memberGradeJob();
        JobParameters jobParameters = new JobParameters();

        try {
            jobLauncher.run(job, jobParameters);
        } catch (JobExecutionAlreadyRunningException | JobInstanceAlreadyCompleteException
                 | JobParametersInvalidException | JobRestartException e) {
            log.error(e.getMessage());
        }

        log.info("등급 쿠폰 스케줄러 끝 시간: {}", LocalDateTime.now());
    }

}
