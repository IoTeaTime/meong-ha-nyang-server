package org.ioteatime.meonghanyangserver.member.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "회원 닉네임과 소속된 그룹 이름 변경 요청")
public record UpdateNicknameAndGroupNameRequest(
        @Schema(description = "수정할 닉네임", example = "new-meonghaynag") String nickname,
        @Schema(description = "수정할 그룹 이름", example = "new-group-name") String groupName) {}
