package org.ioteatime.meonghanyangserver.image.mapper;

import java.util.List;
import org.ioteatime.meonghanyangserver.image.dto.response.GroupDateImageResponse;
import org.ioteatime.meonghanyangserver.image.dto.response.ImageResponse;
import org.ioteatime.meonghanyangserver.image.dto.response.ImageSaveUrlResponse;

public class ImageResponseMapper {
    public static GroupDateImageResponse from(List<ImageResponse> images) {
        return new GroupDateImageResponse(images);
    }

    public static ImageSaveUrlResponse form(String presignedUrl) {
        return new ImageSaveUrlResponse(presignedUrl);
    }
}
