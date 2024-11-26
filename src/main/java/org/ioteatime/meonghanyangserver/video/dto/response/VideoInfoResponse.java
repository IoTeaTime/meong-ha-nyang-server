package org.ioteatime.meonghanyangserver.video.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

@Schema(description = "동영상 정보 조회 응답")
public record VideoInfoResponse (
        @NotBlank @Schema() Long videoId
){
}
