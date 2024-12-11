package org.ioteatime.meonghanyangserver.batch.job.image;

import lombok.RequiredArgsConstructor;
import org.ioteatime.meonghanyangserver.image.domain.ImageEntity;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@RequiredArgsConstructor
public class OldImageDeleteJobConfig {
    private final ImageItemReader itemReader;
    private final ImageItemWriter itemWriter;
    private final ImageItemProcessor itemProcessor;
    private final ImageDeleteJobExecutionListener imageDeleteJobExecutionListener;
    private final PlatformTransactionManager transactionManager;

    @Bean
    @JobScope
    public Step oldOrRemovedImageDeleteStep(JobRepository jobRepository) {
        return new StepBuilder("oldOrRemovedImageDeleteStep", jobRepository)
                .<ImageEntity, ImageEntity>chunk(10, transactionManager)
                .reader(itemReader)
                .processor(itemProcessor)
                .writer(itemWriter)
                .allowStartIfComplete(true)
                .build();
    }

    @Bean
    public Job oldOrRemovedImageDeleteJob(JobRepository jobRepository) {
        return new JobBuilder("oldOrRemovedImageDeleteJob", jobRepository)
                .listener(imageDeleteJobExecutionListener)
                .start(oldOrRemovedImageDeleteStep(jobRepository))
                .build();
    }
}
