package org.ioteatime.meonghanyangserver.group.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Schema(description = "그룹 생성 정보 응답")
public record CreateGroupResponse(
        @NotNull Long groupId, @NotBlank String groupName, @NotNull LocalDateTime createdAt) {}
