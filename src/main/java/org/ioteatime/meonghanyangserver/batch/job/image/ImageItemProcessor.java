package org.ioteatime.meonghanyangserver.batch.job.image;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.ioteatime.meonghanyangserver.clients.s3.S3Client;
import org.ioteatime.meonghanyangserver.image.domain.ImageEntity;
import org.ioteatime.meonghanyangserver.video.domain.VideoEntity;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@StepScope
@RequiredArgsConstructor
public class ImageItemProcessor implements ItemProcessor<ImageEntity, ImageEntity> {
    private final S3Client s3Client;

    @Override
    public ImageEntity process(ImageEntity item) {
        String imageKey = item.getImagePath();
        // S3에서 이미지 삭제
        s3Client.deleteObject(imageKey);
        return item;
    }
}
