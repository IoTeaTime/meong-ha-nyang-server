package org.ioteatime.meonghanyangserver.video.repository;

import static org.ioteatime.meonghanyangserver.video.domain.QVideoEntity.videoEntity;

import com.querydsl.jpa.impl.JPAQueryFactory;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.ioteatime.meonghanyangserver.video.domain.VideoEntity;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class VideoRepositoryImpl implements VideoRepository {
    private final JpaVideoRepository jpaVideoRepository;
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Optional<VideoEntity> findById(Long videoId) {
        return jpaVideoRepository.findById(videoId);
    }

    @Override
    public void deleteAllByGroupId(Long groupId) {
        jpaVideoRepository.deleteAllByGroupId(groupId);
    }

    @Override
    public List<VideoEntity> findByGroupIdAndCreatedAtContains(Long groupId, LocalDate date) {
        return jpaQueryFactory
                .select(videoEntity)
                .from(videoEntity)
                .where(
                        videoEntity
                                .group
                                .id
                                .eq(groupId)
                                .and(
                                        videoEntity.createdAt.between(
                                                date.atStartOfDay(), date.atTime(LocalTime.MAX))))
                .fetch();
    }
}
