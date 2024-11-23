package org.ioteatime.meonghanyangserver.video.repository;

import org.ioteatime.meonghanyangserver.video.domain.VideoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaVideoRepository extends JpaRepository<VideoEntity, Long> {
    void deleteAllByGroupId(Long groupId);
}
