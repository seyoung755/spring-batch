package io.spring.batch.exercise;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@RequiredArgsConstructor
@EnableBatchProcessing
@Configuration
public class BatchConfiguration {
    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job job_2() {
        return this.jobBuilderFactory.get("job")
                .start(step1_2())
                .build();
    }

    @Bean
    public Step step1_2() {
        return this.stepBuilderFactory.get("step1")
                .tasklet(helloWorldTasklet_2())
                .build();
    }

    @Bean
    public Tasklet helloWorldTasklet_2() {
        return new HelloWorld();
    }

    public static class HelloWorld implements Tasklet {
        private static final String HELLO_WORLD = "Hello, %s";

        @Override
        public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext) throws Exception {
            String name = (String) chunkContext.getStepContext()
                    .getJobParameters()
                    .get("name");

            ExecutionContext context = chunkContext.getStepContext()
                    .getStepExecution()
                    .getExecutionContext();

            context.put("name", name);

            System.out.println(String.format(HELLO_WORLD, name));

            return RepeatStatus.FINISHED;
        }
    }
}
