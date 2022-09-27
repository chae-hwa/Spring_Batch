package com.ll.exam.spring_batch.job.helloWorld;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
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
                .start(helloWorldStep1())
                .build();
    }

    @Bean
    public Step helloWorldStep1(){
        return stepBuilderFactory.get("helloWorldStep1")
                .tasklet((helloWorldTasklet()))
                .build();
    }

    @Bean
    public Tasklet helloWorldTasklet(){
        return ((contribution, chunkContext) -> {
            System.out.println("헬로 월드!");

            return RepeatStatus.FINISHED;
        });
    }

}
