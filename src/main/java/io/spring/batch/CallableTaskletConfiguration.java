package io.spring.batch;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.step.tasklet.CallableTaskletAdapter;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.Callable;

@RequiredArgsConstructor
@EnableBatchProcessing
@Configuration
public class CallableTaskletConfiguration {
    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job callableJob() {
        return this.jobBuilderFactory.get("callableJob")
                .start(callableStep())
                .build();
    }

    @Bean
    public Step callableStep() {
        return this.stepBuilderFactory.get("callableStep")
                .tasklet(tasklet())
                .build();
    }

    @Bean
    public CallableTaskletAdapter tasklet() {
        CallableTaskletAdapter callableTaskletAdapter = new CallableTaskletAdapter();

        callableTaskletAdapter.setCallable(callableObject());

        return callableTaskletAdapter;
    }

    @Bean
    public Callable<RepeatStatus> callableObject() {
        return () -> {
            System.out.println("This was executed in another thread"); // 별개의 스레드에서 동작하지만 병렬적으로 실행되진 않는다.
            return RepeatStatus.FINISHED;
        };
    }
}
