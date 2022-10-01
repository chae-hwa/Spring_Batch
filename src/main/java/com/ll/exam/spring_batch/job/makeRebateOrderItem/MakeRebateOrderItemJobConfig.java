package com.ll.exam.spring_batch.job.makeRebateOrderItem;

import com.ll.exam.spring_batch.app.order.entity.OrderItem;
import com.ll.exam.spring_batch.app.order.entity.RebateOrderItem;
import com.ll.exam.spring_batch.app.order.repository.OrderItemRepository;
import com.ll.exam.spring_batch.app.order.repository.RebateOrderItemRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.data.RepositoryItemReader;
import org.springframework.batch.item.data.builder.RepositoryItemReaderBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Sort;

import java.util.Arrays;
import java.util.Collections;


@Configuration
@RequiredArgsConstructor
@Slf4j
public class MakeRebateOrderItemJobConfig {
    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    private final OrderItemRepository orderItemRepository;
    private final RebateOrderItemRepository rebateOrderItemRepository;


    @Bean
    public Job makeRebateOrderItemJob(Step makeRebateOrderItemStep1, CommandLineRunner initData) throws Exception {
        initData.run();

        return jobBuilderFactory.get("makeRebateOrderItemJob")
                .start(makeRebateOrderItemStep1)
                .build();
    }

    @Bean
    @JobScope
    public Step makeRebateOrderItemStep1(
            ItemReader orderItemReader,
            ItemProcessor orderItemToRebateOrderItemProcessor,
            ItemWriter rebateOrderItemWriter
    ) {
        return stepBuilderFactory.get("makeRebateOrderItemStep1")
                .<OrderItem, RebateOrderItem>chunk(100)
                .reader(orderItemReader)
                .processor(orderItemToRebateOrderItemProcessor)
                .writer(rebateOrderItemWriter)
                .build();
    }

    @StepScope
    @Bean
    public RepositoryItemReader<OrderItem> orderItemReader(
            @Value("#{jobParameters['fromId']}") long fromId,
            @Value("#{jobParameters['toId']}") long toId
    ) {
        return new RepositoryItemReaderBuilder<OrderItem>()
                .name("orderItemReader")
                .repository(orderItemRepository) // orderItemRepository에서

                .methodName("findAllByIdBetween") // findAllByIdLessThan 메서드를 불러와라.
                .pageSize(100)
                .arguments(Arrays.asList(fromId, toId)) // 6개씩
                .methodName("findAllByIdLessThan") // findAllByIdLessThan 메서드를 불러와라.
                .pageSize(100)
                .arguments(Arrays.asList(6L)) // 6개씩
                .sorts(Collections.singletonMap("id", Sort.Direction.ASC))
                .build();
    }

    @StepScope
    @Bean
    public ItemProcessor<OrderItem, RebateOrderItem> orderItemToRebateOrderItemProcessor() {
        return orderItem -> new RebateOrderItem(orderItem);
    }

    @StepScope
    @Bean
    public ItemWriter<RebateOrderItem> rebateOrderItemWriter() {
        return items -> items.forEach(item -> {
            RebateOrderItem oldRebateOrderItem = rebateOrderItemRepository.findByOrderItemId(item.getOrderItem().getId()).orElse(null);

            if (oldRebateOrderItem != null) {
                rebateOrderItemRepository.delete(oldRebateOrderItem);
            }

            rebateOrderItemRepository.save(item);
        });
    }
}