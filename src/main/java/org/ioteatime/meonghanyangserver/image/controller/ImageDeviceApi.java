package org.ioteatime.meonghanyangserver.image.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.ioteatime.meonghanyangserver.common.api.Api;
import org.ioteatime.meonghanyangserver.image.dto.response.ImageSaveUrlResponse;
import org.springframework.web.bind.annotation.PathVariable;

@Tag(name = "Image Api", description = "Image 관련 API 목록입니다.")
public interface ImageDeviceApi {
    @Operation(summary = "이미지 저장을 위한 presigned url을 발급 받습니다.", description = "담당자: 임지인")
    public Api<ImageSaveUrlResponse> getImageSaveUrl(
            @PathVariable Long cctvId, @PathVariable String fileName);
}
