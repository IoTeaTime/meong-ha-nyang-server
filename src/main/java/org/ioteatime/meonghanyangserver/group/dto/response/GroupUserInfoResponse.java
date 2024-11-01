package org.ioteatime.meonghanyangserver.group.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Schema(description = "그룹 유저 정보 응답")
public record GroupUserInfoResponse(@NotNull Long userId, @NotBlank String nickname) {}
