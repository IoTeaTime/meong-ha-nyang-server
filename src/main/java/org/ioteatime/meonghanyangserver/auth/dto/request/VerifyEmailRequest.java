package org.ioteatime.meonghanyangserver.auth.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

public record VerifyEmailRequest(
        @NotNull @Schema(description = "이메일", example = "example@gmail.com") @Email String email,
        @NotNull @Schema(description = "인증 코드", example = "XV23W1") String code) {}
