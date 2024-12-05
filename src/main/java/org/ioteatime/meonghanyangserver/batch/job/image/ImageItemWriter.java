package org.ioteatime.meonghanyangserver.batch.job.image;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.ioteatime.meonghanyangserver.image.domain.ImageEntity;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@StepScope
@RequiredArgsConstructor
public class ImageItemWriter implements ItemWriter<ImageEntity> {
    @PersistenceContext private EntityManager em;

    @Override
    public void write(Chunk<? extends ImageEntity> chunk) {
        List<? extends ImageEntity> imageEntities = chunk.getItems();
        List<Long> videoIds = imageEntities.stream().map(ImageEntity::getId).toList();

        if (videoIds.isEmpty()) return;

        em.createQuery("DELETE FROM ImageEntity WHERE id IN (:videoIds)")
                .setParameter("videoIds", videoIds)
                .executeUpdate();
    }
}
