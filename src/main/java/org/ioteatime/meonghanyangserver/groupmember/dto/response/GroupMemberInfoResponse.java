package org.ioteatime.meonghanyangserver.groupmember.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.ioteatime.meonghanyangserver.groupmember.doamin.enums.GroupMemberRole;

@Schema(description = "디바이스 정보 응답")
public record GroupMemberInfoResponse(
        @NotNull Long groupMemberId,
        @NotNull Long memberId,
        @NotBlank String nickname,
        @NotBlank String thingId,
        @NotBlank GroupMemberRole role) {}
