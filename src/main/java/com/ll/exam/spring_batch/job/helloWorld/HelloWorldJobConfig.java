package com.ll.exam.spring_batch.job.helloWorld;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class HelloWorldJobConfig {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job helloWorldJob(){ //Job 이름은 무조건 해야 된다. (딱 1개)
        return jobBuilderFactory.get("helloWorldJob")
                .incrementer(new RunIdIncrementer()) // 실행마다 강제로 매번 다른 ID를 파라미터로 부여
                .start(helloWorldStep1())
                .build();
    }

    @Bean
    @JobScope // 해당 Job이 실행될 때 생성
    public Step helloWorldStep1(){
        return stepBuilderFactory.get("helloWorldStep1")
                .tasklet((helloWorldTasklet()))
                .build();
    }

    @Bean
    @StepScope // 해당 Step이 실행될 때 생성
    public Tasklet helloWorldTasklet(){
        return ((contribution, chunkContext) -> {
            System.out.println("헬로 월드!");

            return RepeatStatus.FINISHED;
        });
    }

}
