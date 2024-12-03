package org.ioteatime.meonghanyangserver.batch.scheduler;

import java.util.HashMap;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.ioteatime.meonghanyangserver.batch.job.media.OldMediaDeleteJobConfig;
import org.springframework.batch.core.JobParameter;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class BatchScheduler {
    private final JobLauncher jobLauncher;
    private final OldMediaDeleteJobConfig oldMediaDeleteJobConfig;
    private final JobRepository jobRepository;

    // 매월 1일 7일 이상 오래된 비디오를 삭제하는 배치 작업을 수행
    @Scheduled(cron = "0 0 0 1 * *")
    public void runJob() {
        Map<String, JobParameter<?>> confMap = new HashMap<>();
        confMap.put("time", new JobParameter<>(System.currentTimeMillis(), Long.class));
        JobParameters jobParameters = new JobParameters(confMap);

        try {
            jobLauncher.run(
                    oldMediaDeleteJobConfig.oldMediaDeleteJob(jobRepository), jobParameters);
        } catch (JobExecutionAlreadyRunningException
                | JobInstanceAlreadyCompleteException
                | JobParametersInvalidException
                | org.springframework.batch.core.repository.JobRestartException e) {

            log.error(e.getMessage());
        }
    }
}
