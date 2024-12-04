package org.ioteatime.meonghanyangserver.image.mapper;

import java.util.List;
import org.ioteatime.meonghanyangserver.image.dto.response.GroupDateImageResponse;
import org.ioteatime.meonghanyangserver.image.dto.response.ImageResponse;

public class ImageResponseMapper {
    public static GroupDateImageResponse from(List<ImageResponse> images) {
        return new GroupDateImageResponse(images);
    }
}
