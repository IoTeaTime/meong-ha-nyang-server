package org.ioteatime.meonghanyangserver.video.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

@Schema(description = "동영상 presigned url 조회 응답")
public record VideoPresignedUrlResponse(
        @NotBlank
                @Schema(
                        description = "동영상 presigned url",
                        example = "https://bucket.s3.ap-northeast-2.amazonaws.com/test?")
                String presignedUrl) {}
