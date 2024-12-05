package org.ioteatime.meonghanyangserver.batch.job.image;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import java.time.LocalDateTime;
import java.util.List;
import org.ioteatime.meonghanyangserver.image.domain.ImageEntity;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemReader;
import org.springframework.stereotype.Component;

@Component
@StepScope
public class ImageItemReader implements ItemReader<ImageEntity> {
    @PersistenceContext private EntityManager em;

    private static final int PAGE_SIZE = 10;
    private int curIdx = 0;
    private List<ImageEntity> images;

    @Override
    public ImageEntity read() {
        if (images == null || curIdx >= images.size()) {
            fetchNext();
        }
        if (curIdx < images.size()) {
            return images.get(curIdx++);
        }
        return null;
    }

    private void fetchNext() {
        LocalDateTime twoWeeksAgo = LocalDateTime.now().minusDays(14);
        TypedQuery<ImageEntity> query =
                em.createQuery(
                                "SELECT v FROM ImageEntity v WHERE v.createdAt <= (:twoWeeksAgo)",
                                ImageEntity.class)
                        .setParameter("twoWeeksAgo", twoWeeksAgo)
                        .setFirstResult(curIdx)
                        .setMaxResults(PAGE_SIZE);
        images = query.getResultList();
        curIdx = 0;
    }
}
