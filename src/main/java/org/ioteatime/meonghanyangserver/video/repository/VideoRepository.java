package org.ioteatime.meonghanyangserver.video.repository;

import java.util.Optional;
import org.ioteatime.meonghanyangserver.video.domain.VideoEntity;

public interface VideoRepository {
    Optional<VideoEntity> findById(Long videoId);
}
