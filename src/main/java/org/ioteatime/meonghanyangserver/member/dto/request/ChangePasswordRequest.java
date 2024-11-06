package org.ioteatime.meonghanyangserver.member.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record ChangePasswordRequest(
        @NotBlank(message = "현재 비밀번호는 필수 입력값")
                @Schema(description = "현재 비밀번호", example = "testpassword")
                String currentPassword,
        @NotBlank(message = "새 비밀번호는 필수 입력값")
                @Size(min = 8, max = 20, message = "비밀번호는 8자 이상 20자 이하여야 합니다.")
                @Schema(description = "새 비밀번호", example = "newpassword1234")
                String newPassword) {}
