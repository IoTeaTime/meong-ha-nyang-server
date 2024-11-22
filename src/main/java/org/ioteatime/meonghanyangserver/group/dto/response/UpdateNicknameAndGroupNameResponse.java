package org.ioteatime.meonghanyangserver.group.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(description = "회원 닉네임과 소속된 그룹 이름 변경 응답")
public record UpdateNicknameAndGroupNameResponse(
        @Schema(description = "수정된 닉네임", example = "new-meonghaynag") String nickname,
        @Schema(description = "수정된 그룹 이름", example = "new-group-name") String groupName) {}
