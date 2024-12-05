package org.ioteatime.meonghanyangserver.image.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

@Schema(description = "이미지 저장 Url 요청 응답")
public record ImageSaveUrlResponse(
        @NotNull
                @Schema(
                        description = "이미지 저장용 presigned url",
                        example = "https://bucket.s3.ap-northeast-2.amazonaws.com/test?")
                String presignedUrl) {}
