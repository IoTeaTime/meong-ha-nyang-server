package org.ioteatime.meonghanyangserver.auth.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

public record LoginRequest(
        @NotBlank @Schema(description = "이메일", example = "test@gmail.com") String email,
        @NotBlank @Schema(description = "비밀번호", example = "testpassword") String password) {}
