package com.nhnacademy.marketgg.server.step;

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

@Configuration
@RequiredArgsConstructor
public class UpdateMemberGradeStep {

    private final EntityManagerFactory entityManagerFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final MemberGradeRepository memberGradeRepository;
    private final OrderRepository orderRepository;

    private static final int CHUNK_SIZE = 5;
    private static final Long GVIP = 1L;
    private static final Long VIP = 2L;
    private static final Long MEMBER = 3L;

    @Bean
    public Step memberGradeUpdateStep() {
        return stepBuilderFactory.get("updateMemberGradeStep")
                                 .<Member, Member>chunk(CHUNK_SIZE)
                                 .reader(allMemberReader())
                                 .processor(updateGradeProcessor())
                                 .writer(allMemberWriter())
                                 .allowStartIfComplete(true)
                                 .build();
    }

    @Bean
    public JpaPagingItemReader<Member> allMemberReader() {

        return new JpaPagingItemReaderBuilder<Member>()
            .queryString("SELECT m FROM Member m")
            .pageSize(CHUNK_SIZE)
            .entityManagerFactory(entityManagerFactory)
            .name("allMemberReader")
            .build();
    }

    @Bean
    public ItemProcessor<Member, Member> updateGradeProcessor() {

        return member -> {
            Long memberGrade;
            Long account = totalAccountForMonth(member);

            if (account < 300000L) {
                memberGrade = MEMBER;
            } else if (account < 500000L) {
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

    @Bean
    public JpaItemWriter<Member> allMemberWriter() {
        JpaItemWriter<Member> writer = new JpaItemWriter<>();
        writer.setEntityManagerFactory(entityManagerFactory);
        return writer;
    }

    private Long totalAccountForMonth(Member member) {
        List<Order> orderByMonth = orderRepository.findOrderByMonth(member);
        Long totalAmount = 0L;
        for (Order order : orderByMonth) {
            totalAmount += order.getTotalAmount();
        }
        return totalAmount;
    }

}
