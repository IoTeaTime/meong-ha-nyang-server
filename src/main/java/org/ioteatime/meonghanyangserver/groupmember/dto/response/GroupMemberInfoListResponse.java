package org.ioteatime.meonghanyangserver.groupmember.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;

@Schema(description = "그룹 참여자 정보 목록 조회 응답")
public record GroupMemberInfoListResponse(
        @Schema(description = "그룹 참여자 정보 목록") List<GroupMemberInfoResponse> member) {}
