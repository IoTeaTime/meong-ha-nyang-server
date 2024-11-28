package org.ioteatime.meonghanyangserver.video.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.ioteatime.meonghanyangserver.video.domain.VideoEntity;

public interface VideoRepository {
    Optional<VideoEntity> findById(Long videoId);

    void deleteAllByGroupId(Long groupId);

    List<VideoEntity> findByGroupIdAndCreatedAtContains(Long groupId, LocalDate date);
}
