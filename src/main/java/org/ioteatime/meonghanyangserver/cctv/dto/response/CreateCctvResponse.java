package org.ioteatime.meonghanyangserver.cctv.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

@Schema(description = "CCTV 생성 응답")
public record CreateCctvResponse(
        @NotNull @Schema(description = "CCTV ID", example = "1") Long cctvId,
        @NotNull
                @Schema(
                        description = "CCTV AccessToken",
                        example = "Bearer eyJ23Gc2fNCJ9.eywguYW161...")
                String accessToken) {}
