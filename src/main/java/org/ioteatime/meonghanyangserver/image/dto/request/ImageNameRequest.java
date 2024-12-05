package org.ioteatime.meonghanyangserver.image.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

@Schema(description = "이미지 이름 요청")
public record ImageNameRequest(
        @NotNull @Schema(description = "이미지 이름", example = "test-image.jpg") String imageName) {}
