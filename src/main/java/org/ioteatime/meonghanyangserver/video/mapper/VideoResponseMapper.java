package org.ioteatime.meonghanyangserver.video.mapper;

import org.ioteatime.meonghanyangserver.video.domain.VideoEntity;
import org.ioteatime.meonghanyangserver.video.dto.response.VideoInfoResponse;
import org.ioteatime.meonghanyangserver.video.dto.response.VideoPresignedUrlResponse;

public class VideoResponseMapper {
    public static VideoPresignedUrlResponse form(String presignedURL) {
        return new VideoPresignedUrlResponse(presignedURL);
    }

    public static VideoInfoResponse form(VideoEntity videoEntity, String presignedURL) {
        return new VideoInfoResponse(videoEntity.getId(), presignedURL, videoEntity.getCreatedAt());
    }
}
