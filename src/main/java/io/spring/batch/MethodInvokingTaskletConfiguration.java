package io.spring.batch;

import io.spring.batch.service.CustomService;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.step.tasklet.MethodInvokingTaskletAdapter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@RequiredArgsConstructor
@EnableBatchProcessing
@Configuration
public class MethodInvokingTaskletConfiguration {
    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job methodInvokingJob() {
        return this.jobBuilderFactory.get("methodInvokingJob")
                .start(methodInvokingStep())
                .build();
    }

    @Bean
    public Step methodInvokingStep() {
        return this.stepBuilderFactory.get("methodInvokingStep")
                .tasklet(methodInvokingTasklet())
                .build();
    }

    @Bean
    public MethodInvokingTaskletAdapter methodInvokingTasklet() {
        MethodInvokingTaskletAdapter methodInvokingTaskletAdapter = new MethodInvokingTaskletAdapter();

        methodInvokingTaskletAdapter.setTargetObject(service());
        methodInvokingTaskletAdapter.setTargetMethod("serviceMethod");

        return methodInvokingTaskletAdapter;
    }

    @Bean
    public CustomService service() {
        return new CustomService();
    }
}
