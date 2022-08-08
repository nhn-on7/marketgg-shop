package com.nhnacademy.marketgg.server.batchstep;

import com.nhnacademy.marketgg.server.entity.Member;
import com.nhnacademy.marketgg.server.entity.MemberGrade;
import com.nhnacademy.marketgg.server.entity.Order;
import com.nhnacademy.marketgg.server.exception.membergrade.MemberGradeNotFoundException;
import com.nhnacademy.marketgg.server.repository.membergrade.MemberGradeRepository;
import com.nhnacademy.marketgg.server.repository.order.OrderRepository;
import java.util.List;
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

/**
 * 회원을 모두 조회하여 등급을 업데이트하는 Batch Step 과 step process(reader, processor, writer) 입니다.
 *
 * @author 민아영
 * @version 1.0.0
 */
@Configuration
@RequiredArgsConstructor
public class UpdateMemberGradeStep {

    private final EntityManagerFactory entityManagerFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final MemberGradeRepository memberGradeRepository;
    private final OrderRepository orderRepository;

    private static final int CHUNK_SIZE = 5;
    private static final long GVIP = 1L;
    private static final long VIP = 2L;
    private static final long MEMBER = 3L;

    /**
     * 회원을 모두 조회하고 등급을 업데이트하고 저장하는 Step 입니다.
     * chunk 는 처리하는 수행단위 입니다. commit 되는 트랜잭션 단위와 같습니다.
     *
     * @return Step - process(reader, processor, writer) 설정을 stepBuilderFactory 가 빌드하여 반환한다.
     * @author 민아영
     * @since 1.0.0
     */
    @Bean
    public Step memberGradeUpdateStep() {
        return stepBuilderFactory.get("memberGradeUpdateStep")
                                 .<Member, Member>chunk(CHUNK_SIZE)
                                 .reader(allMemberReader())
                                 .processor(updateGradeProcessor())
                                 .writer(allMemberWriter())
                                 .allowStartIfComplete(true)
                                 .build();
    }

    /**
     * 회원을 모두 조회하는 reader 입니다.
     * page_size 와 chunk_size 는 똑같은 값으로 설정 했습니다.
     *
     * @return 조회한 Member 리스트를 JpaPagingItemReaderBuilder 로 빌드하여 반환합니다.
     * @author 민아영
     * @since 1.0.0
     */
    @Bean
    public JpaPagingItemReader<Member> allMemberReader() {

        return new JpaPagingItemReaderBuilder<Member>()
            .queryString("SELECT m FROM Member m")
            .pageSize(CHUNK_SIZE)
            .entityManagerFactory(entityManagerFactory)
            .name("allMemberReader")
            .build();
    }

    /**
     * 회원의 등급 정보를 업데이트 하기 위한 비지니스 로직이 작성된 processor 입니다.
     *
     * @return 등급 정보를 업데이트한 회원을 반환합니다.
     * @author 민아영
     * @since 1.0.0
     */
    @Bean
    public ItemProcessor<Member, Member> updateGradeProcessor() {

        return member -> {
            long memberGrade;
            long account = totalAccountForMonth(member);

            if (account < 300_000L) {
                memberGrade = MEMBER;
            } else if (account < 500_000L) {
                memberGrade = VIP;
            } else {
                memberGrade = GVIP;
            }
            MemberGrade grade = memberGradeRepository.findById(memberGrade)
                                                     .orElseThrow(MemberGradeNotFoundException::new);
            member.updateGrade(grade);
            return member;
        };
    }

    /**
     * 등급이 업데이트 된 회원들을 DB 에 저장하는 writer 입니다.
     *
     * @return 회원들의 정보를 담은 writer 를 반환합니다.
     * @author 민아영
     * @since 1.0.0
     */
    @Bean
    public JpaItemWriter<Member> allMemberWriter() {
        JpaItemWriter<Member> writer = new JpaItemWriter<>();
        writer.setEntityManagerFactory(entityManagerFactory);
        return writer;
    }

    /**
     * 한달 동안 회원의 총 구매 금액을 계산합니다.
     *
     * @param member - 조회할 회원입니다.
     * @return 회원의 주문 내역에서 결제 금액을 다 합산하여 총 구매 금액을 반환한다.
     * @author 민아영
     * @since 1.0.0
     */
    private Long totalAccountForMonth(Member member) {
        List<Order> orderByMonth = orderRepository.findOrderByMonth(member);

        return orderByMonth.stream().mapToLong(Order::getTotalAmount).sum();
    }

}
