package org.ioteatime.meonghanyangserver.image.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

@Schema(description = "이미지 저장 Url 요청 응답")
public record ImageSaveUrlResponse(
        @NotNull
                @Schema(
                        description = "이미지 저장용 presigned url",
                        example = "https://bucket.s3.ap-northeast-2.amazonaws.com/test?")
                String presignedUrl,
        @NotNull @Schema(description = "이미지 이름", example = "test-image.jpg") String imageName,
        @NotNull
                @Schema(
                        description = "S3의 이미지 저장 경로",
                        example = "img/Mhn_capture_61733381438603.jpg")
                String imagePath) {}
