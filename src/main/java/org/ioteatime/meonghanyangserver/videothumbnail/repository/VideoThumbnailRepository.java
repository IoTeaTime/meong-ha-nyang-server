package org.ioteatime.meonghanyangserver.videothumbnail.repository;

import org.ioteatime.meonghanyangserver.videothumbnail.domain.VideoThumbnailEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VideoThumbnailRepository extends JpaRepository<VideoThumbnailEntity, Long> {}
