package org.ioteatime.meonghanyangserver.auth.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

public record EmailRequest(
        @Valid @Email @NotNull @Schema(description = "이메일", example = "example@gmail.com")
                String email) {}
