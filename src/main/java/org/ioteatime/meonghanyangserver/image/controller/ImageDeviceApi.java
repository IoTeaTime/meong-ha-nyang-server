package org.ioteatime.meonghanyangserver.image.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.ioteatime.meonghanyangserver.common.api.Api;
import org.ioteatime.meonghanyangserver.common.utils.LoginMember;
import org.ioteatime.meonghanyangserver.image.dto.request.FinishUploadRequest;
import org.ioteatime.meonghanyangserver.image.dto.request.ImageNameRequest;
import org.ioteatime.meonghanyangserver.image.dto.response.ImageSaveUrlResponse;
import org.springframework.web.bind.annotation.RequestBody;

@Tag(name = "Image Api", description = "Image 관련 API 목록입니다.")
public interface ImageDeviceApi {
    @Operation(
            summary = "이미지 저장을 위한 presigned url을 발급 받습니다.",
            description =
                    "담당자: 임지인\n\n캡쳐 이미지 저장을 위한 URL 발급 요청입니다.\n\nCCTV의 Access Token과 함께 요청 하시면 됩니다.")
    Api<ImageSaveUrlResponse> getImageSaveUrl(
            @LoginMember Long cctvId, @RequestBody ImageNameRequest imageNameRequest);

    @Operation(
            summary = "이미지 저장에 성공시에 DB에 저장합니다",
            description =
                    "담당자: 임지인\n\n이미지 저장 후 API를 호출하여 DB에 반영합니다.\n\n 이미지 이름과 함께 URL에서 경로를 추출하고 요청 하시면 됩니다.")
    Api<Object> imageSaveSuccess(
            @LoginMember Long cctvId, @RequestBody FinishUploadRequest finishUploadRequest);
}
