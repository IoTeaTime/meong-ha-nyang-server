package org.ioteatime.meonghanyangserver.image.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

@Schema(description = "S3 업로드 성공 응답 요청")
public record FinishUploadRequest(
        @NotNull @Schema(description = "이미지 이름", example = "test-image.jpg") String imageName,
        @NotNull @Schema(description = "파일 경로", example = "img/Mhn_capture_61733381438603.jpg")
                String imagePath) {}
