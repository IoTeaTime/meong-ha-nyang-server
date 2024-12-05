package org.ioteatime.meonghanyangserver.video.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

@Schema(description = "동영상 이름 요청")
public record VideoNameRequest(
        @NotNull @Schema(description = "동영상 이름", example = "test-video.mp4") String videoName) {}
