package org.ioteatime.meonghanyangserver.group.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

@Schema(description = "그룹 정보 응답")
public record GroupResponse(@NotNull @Schema(description = "그룹 ID", example = "1") Long groupId) {}
