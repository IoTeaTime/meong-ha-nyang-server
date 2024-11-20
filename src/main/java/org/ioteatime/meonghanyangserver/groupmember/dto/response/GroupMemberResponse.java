package org.ioteatime.meonghanyangserver.groupmember.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.ioteatime.meonghanyangserver.groupmember.doamin.enums.GroupMemberRole;

@Schema(description = "그룹 및 참여자 역할 응답")
public record GroupMemberResponse(
        @NotNull @Schema(description = "group id", example = "1") Long groupId,
        @NotBlank @Schema(description = "그룹 멤버 역할", example = "ROLE_MASTER")
                GroupMemberRole role) {}
