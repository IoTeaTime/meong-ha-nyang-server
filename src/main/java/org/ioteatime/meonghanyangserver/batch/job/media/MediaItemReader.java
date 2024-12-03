package org.ioteatime.meonghanyangserver.batch.job.media;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import java.time.LocalDateTime;
import java.util.List;
import org.ioteatime.meonghanyangserver.video.domain.VideoEntity;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemReader;
import org.springframework.stereotype.Component;

@Component
@StepScope
public class MediaItemReader implements ItemReader<VideoEntity> {
    @PersistenceContext private EntityManager em;

    private static final int PAGE_SIZE = 10;
    private int curIdx = 0;
    private List<VideoEntity> videos;

    @Override
    public VideoEntity read() {
        if (videos == null || curIdx >= videos.size()) {
            fetchNext();
        }
        if (curIdx < videos.size()) {
            return videos.get(curIdx++);
        }
        return null;
    }

    private void fetchNext() {
        LocalDateTime sevenDaysAgo = LocalDateTime.now().minusDays(7);
        TypedQuery<VideoEntity> query =
                em.createQuery(
                                "SELECT v FROM VideoEntity v WHERE v.createdAt <= (:sevenDaysAgo)",
                                VideoEntity.class)
                        .setParameter("sevenDaysAgo", sevenDaysAgo)
                        .setFirstResult(curIdx)
                        .setMaxResults(PAGE_SIZE);
        videos = query.getResultList();
        curIdx = 0;
    }
}
