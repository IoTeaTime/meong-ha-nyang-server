package org.ioteatime.meonghanyangserver.image.mapper;

import org.ioteatime.meonghanyangserver.image.dto.response.ImageSaveUrlResponse;

public class ImageResponseMapper {
    public static ImageSaveUrlResponse form(String presignedUrl) {
        return new ImageSaveUrlResponse(presignedUrl);
    }
}
