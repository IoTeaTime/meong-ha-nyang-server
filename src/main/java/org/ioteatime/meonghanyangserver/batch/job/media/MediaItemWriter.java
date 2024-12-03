package org.ioteatime.meonghanyangserver.batch.job.media;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.ioteatime.meonghanyangserver.video.domain.VideoEntity;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

@Component
@StepScope
@RequiredArgsConstructor
public class MediaItemWriter implements ItemWriter<VideoEntity> {
    @PersistenceContext private EntityManager em;

    @Override
    public void write(Chunk<? extends VideoEntity> chunk) {
        List<? extends VideoEntity> videoEntities = chunk.getItems();
        List<Long> videoIds = videoEntities.stream().map(VideoEntity::getId).toList();

        if (videoIds.isEmpty()) return;

        em.createQuery("DELETE FROM VideoEntity WHERE id IN (:videoIds)")
                .setParameter("videoIds", videoIds)
                .executeUpdate();
    }
}
