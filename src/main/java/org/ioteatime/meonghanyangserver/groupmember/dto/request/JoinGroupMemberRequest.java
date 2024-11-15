package org.ioteatime.meonghanyangserver.groupmember.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

@Schema(description = "Viewer가 그룹에 참여하기 위한 요청")
public record JoinGroupMemberRequest(
        @NotNull @Schema(description = "그룹 ID", example = "1") Long groupId,
        @NotNull @Schema(description = "Thing ID", example = "cf27414r3b22c023") String thingId) {}
