package org.ioteatime.meonghanyangserver.video.mapper;

import org.ioteatime.meonghanyangserver.video.dto.response.VideoPresignedUrlResponse;

public class VideoResponseMapper {
    public static VideoPresignedUrlResponse form(String presignedURL) {
        return new VideoPresignedUrlResponse(presignedURL);
    }
}
