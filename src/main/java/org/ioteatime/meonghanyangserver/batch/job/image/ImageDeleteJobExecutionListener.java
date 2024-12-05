package org.ioteatime.meonghanyangserver.batch.job.image;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ImageDeleteJobExecutionListener implements JobExecutionListener {
    @Override
    public void beforeJob(JobExecution jobExecution) {
        LocalDateTime now = LocalDateTime.now();
        log.info(
                "[{}] - 7일이 지난 이미지 삭제 배치 작업을 시작합니다.",
                now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
    }

    @Override
    public void afterJob(JobExecution jobExecution) {
        if (jobExecution.getStatus().isUnsuccessful()) {
            log.info("이미지 삭제 배치 작업이 실패하였습니다.");
        } else if (jobExecution.getStatus().isLessThanOrEqualTo(BatchStatus.COMPLETED)) {
            log.info("이미지 삭제 배치 작업이 성공하였습니다.");
            log.info("이미지 삭제 배치 작업을 종료합니다.");
        }
    }
}
