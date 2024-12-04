package org.ioteatime.meonghanyangserver.image.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

@Getter
@Schema(description = "이미지 단일 응답")
public class ImageResponse {
    @NotNull
    @Schema(description = "이미지 ID", example = "1")
    private final Long imageId;

    @NotNull
    @Schema(description = "이미지 원본 이름", example = "test-image.jpg")
    private final String imageName;

    @NotNull
    @Schema(
            description = "이미지 URL",
            example = "https://bucket.s3.ap-northeast-2.amazonaws.com/path/to/test-image.jpg")
    private String imagePath;

    @NotNull
    @Schema(description = "형식이 있는 이미지 생성 시각", example = "2024.10.22.13:00")
    private final String formattedCreatedAt;

    @Builder
    public ImageResponse(
            Long imageId, String imageName, String imagePath, String formattedCreatedAt) {
        this.imageId = imageId;
        this.imageName = imageName;
        this.imagePath = imagePath;
        this.formattedCreatedAt = formattedCreatedAt;
    }

    public ImageResponse updateToPresignedUrl(String imagePresignedPath) {
        this.imagePath = imagePresignedPath;
        return this;
    }
}
