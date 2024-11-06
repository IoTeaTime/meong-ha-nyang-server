package org.ioteatime.meonghanyangserver.groupmember.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.ioteatime.meonghanyangserver.groupmember.doamin.enums.GroupMemberRole;

@Schema(description = "그룹 멤버 정보 응답")
public record GroupMemberInfoResponse(
        @NotNull @Schema(description = "group 참여자 id", example = "1") Long groupMemberId,
        @NotNull @Schema(description = "유저 id", example = "1") Long memberId,
        @NotBlank @Schema(description = "유저 nickname", example = "홍길동") String nickname,
        @NotBlank @Schema(description = "iot thing id") String thingId,
        @NotBlank @Schema(description = "그룹 멤버 역할", example = "ROLE_MASTER")
                GroupMemberRole role) {}
