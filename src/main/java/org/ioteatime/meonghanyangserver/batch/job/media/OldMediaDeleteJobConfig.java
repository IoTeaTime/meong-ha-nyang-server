package org.ioteatime.meonghanyangserver.batch.job.media;

import lombok.RequiredArgsConstructor;
import org.ioteatime.meonghanyangserver.video.domain.VideoEntity;
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
public class OldMediaDeleteJobConfig {
    private final MediaItemReader mediaItemReader;
    private final MediaItemWriter mediaItemWriter;
    private final MediaItemProcessor mediaItemProcessor;
    private final MediaDeleteJobExecutionListener mediaDeleteJobExecutionListener;
    private final PlatformTransactionManager transactionManager;

    @Bean
    @JobScope
    public Step oldMediaDeleteStep(JobRepository jobRepository) {
        return new StepBuilder("oldMediaDeleteStep", jobRepository)
                .<VideoEntity, VideoEntity>chunk(10, transactionManager)
                .reader(mediaItemReader)
                .processor(mediaItemProcessor)
                .writer(mediaItemWriter)
                .allowStartIfComplete(true)
                .build();
    }

    @Bean
    public Job oldMediaDeleteJob(JobRepository jobRepository) {
        return new JobBuilder("oldMediaDeleteJob", jobRepository)
                .listener(mediaDeleteJobExecutionListener)
                .start(oldMediaDeleteStep(jobRepository))
                .build();
    }
}
