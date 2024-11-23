package org.ioteatime.meonghanyangserver.cctv.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

@Schema(description = "CCTV 이름 수정 요청")
public record UpdateCctvNickname(
        @NotNull @Schema(description = "수정할 CCTV ID", example = "1") Long cctvId,
        @NotNull @Schema(description = "수정할 CCTV 이름", example = "멍하냥CCTV") String cctvNickname) {}
