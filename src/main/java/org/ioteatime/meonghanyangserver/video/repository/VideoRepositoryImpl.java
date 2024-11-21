package org.ioteatime.meonghanyangserver.video.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
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
}
