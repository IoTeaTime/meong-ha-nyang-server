package org.ioteatime.meonghanyangserver.auth.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record LoginRequest(
        @NotBlank @Schema(description = "이메일", example = "test@gmail.com") @Email String email,
        @NotBlank
                @Schema(description = "비밀번호", example = "testpassword")
                @Size(min = 8, max = 20, message = "비밀번호는 8자 이상 20자 이하여야 합니다.")
                String password) {}
