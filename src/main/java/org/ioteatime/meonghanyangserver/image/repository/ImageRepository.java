package org.ioteatime.meonghanyangserver.image.repository;

import java.time.LocalDateTime;
import java.util.List;
import org.ioteatime.meonghanyangserver.image.domain.ImageEntity;
import org.ioteatime.meonghanyangserver.image.dto.response.ImageResponse;

public interface ImageRepository {
    List<ImageResponse> findAllByGroupIdAndDate(Long groupId, LocalDateTime searchDate);

    ImageEntity save(ImageEntity imageEntity);
}
