package org.ioteatime.meonghanyangserver.groupmember.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.ioteatime.meonghanyangserver.common.api.Api;
import org.ioteatime.meonghanyangserver.common.utils.LoginMember;
import org.springframework.web.bind.annotation.PathVariable;

@Tag(name = "Group Member Api", description = "Group Member 관련 API 목록입니다.")
public interface GroupMemberApi {

    @Operation(summary = "방장이 그룹에서 참여자를 제외합니다.", description = "담당자: 최민석")
    public Api<?> deleteMasterGroupMember(
            @LoginMember Long memberId,
            @PathVariable Long groupId,
            @PathVariable Long groupMemberId);
}
