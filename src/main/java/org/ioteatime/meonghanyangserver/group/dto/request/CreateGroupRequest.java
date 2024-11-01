package org.ioteatime.meonghanyangserver.group.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
@Schema(description = "그룹 생성 정보 요청")
public record CreateGroupRequest(@NotBlank String deviceUuid) {
}
