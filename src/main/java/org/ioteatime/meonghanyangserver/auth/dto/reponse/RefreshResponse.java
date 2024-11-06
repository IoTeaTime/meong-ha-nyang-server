package org.ioteatime.meonghanyangserver.auth.dto.reponse;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

@Schema(description = "갱신된 AccessToken 입니다.")
public record RefreshResponse(
        @NotNull @Schema(description = "갱신된 AccessToken", example = "Bearer wefewfwe...")
                String accessToken) {}
