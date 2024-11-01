package org.ioteatime.meonghanyangserver.device.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.ioteatime.meonghanyangserver.device.doamin.enums.DeviceRole;

@Schema(description = "디바이스 정보 응답")
public record DeviceInfoResponse(
        @NotNull Long userId, @NotBlank String nickname, @NotBlank DeviceRole role) {}
