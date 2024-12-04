package org.ioteatime.meonghanyangserver.image.controller;

import lombok.RequiredArgsConstructor;
import org.ioteatime.meonghanyangserver.common.api.Api;
import org.ioteatime.meonghanyangserver.common.type.ImageSuccessType;
import org.ioteatime.meonghanyangserver.common.utils.LoginMember;
import org.ioteatime.meonghanyangserver.image.dto.response.ImageSaveUrlResponse;
import org.ioteatime.meonghanyangserver.image.service.ImageService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/image-device")
public class ImageDeviceController implements ImageDeviceApi {
    private final ImageService imageService;

    @GetMapping("/{fileName}")
    public Api<ImageSaveUrlResponse> getImageSaveUrl(
            @LoginMember Long cctvId, @PathVariable String fileName) {
        ImageSaveUrlResponse imageSaveUrlResponse = imageService.getImageSaveUrl(cctvId, fileName);
        return Api.success(ImageSuccessType.CREATE_PRESIGNED_URL, imageSaveUrlResponse);
    }
}
