package org.ioteatime.meonghanyangserver.image.controller;

import lombok.RequiredArgsConstructor;
import org.ioteatime.meonghanyangserver.common.api.Api;
import org.ioteatime.meonghanyangserver.common.type.ImageSuccessType;
import org.ioteatime.meonghanyangserver.common.utils.LoginMember;
import org.ioteatime.meonghanyangserver.image.dto.request.FinishUploadRequest;
import org.ioteatime.meonghanyangserver.image.dto.request.ImageNameRequest;
import org.ioteatime.meonghanyangserver.image.dto.response.ImageSaveUrlResponse;
import org.ioteatime.meonghanyangserver.image.service.ImageService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/image-device")
public class ImageDeviceController implements ImageDeviceApi {
    private final ImageService imageService;

    @PostMapping("/presigned-url")
    public Api<ImageSaveUrlResponse> getImageSaveUrl(
            @LoginMember Long cctvId, @RequestBody ImageNameRequest imageNameRequest) {
        ImageSaveUrlResponse imageSaveUrlResponse =
                imageService.getImageSaveUrl(cctvId, imageNameRequest.imageName());
        return Api.success(ImageSuccessType.CREATE_PRESIGNED_URL, imageSaveUrlResponse);
    }

    @PostMapping("/complete-upload")
    public Api<Object> imageSaveSuccess(
            @LoginMember Long cctvId, @RequestBody FinishUploadRequest finishUploadRequest) {
        imageService.saveImage(cctvId, finishUploadRequest);
        return Api.success((ImageSuccessType.SUCCESS_UPLOAD_IMAGE));
    }
}
