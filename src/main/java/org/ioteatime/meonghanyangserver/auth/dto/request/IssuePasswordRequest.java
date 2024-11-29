package org.ioteatime.meonghanyangserver.auth.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

@Schema(description = "임시 비밀번호 발급을 위한 요청")
public record IssuePasswordRequest(
        @Email @NotNull @Schema(description = "이메일", example = "example@gmail.com") String email) {}
