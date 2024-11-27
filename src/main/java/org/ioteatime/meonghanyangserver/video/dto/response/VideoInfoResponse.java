package org.ioteatime.meonghanyangserver.video.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Schema(description = "동영상 정보 조회 응답")
public record VideoInfoResponse(
        @NotNull @Schema(description = "동영상 ID", example = "1") Long videoId,
        @NotBlank
                @Schema(
                        description = "썸네일 presigned url",
                        example = "https://bucket.s3.ap-northeast-2.amazonaws.com/test?")
                String thumbnailPresignedUrl,
        @NotBlank @Schema(description = "동영상 생성 날짜", example = "2024-11-26 12:57:45")
                LocalDateTime createdAt) {}
