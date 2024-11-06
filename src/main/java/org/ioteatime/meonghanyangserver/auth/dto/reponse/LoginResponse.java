package org.ioteatime.meonghanyangserver.auth.dto.reponse;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

public record LoginResponse(
        @NotNull @Schema(description = "회원 ID", example = "1") Long memberId,
        @NotBlank @Schema(description = "회원 AccessToken", example = "Bearer abcd...")
                String accessToken,
        @NotBlank @Schema(description = "회원 RefreshToken", example = "Bearer abcd...")
                String refreshToken) {

    @Builder
    public LoginResponse(Long memberId, String accessToken, String refreshToken) {
        this.memberId = memberId;
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }
}
