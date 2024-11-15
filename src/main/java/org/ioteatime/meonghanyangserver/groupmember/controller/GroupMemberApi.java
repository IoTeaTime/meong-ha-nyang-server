package org.ioteatime.meonghanyangserver.groupmember.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.ioteatime.meonghanyangserver.common.api.Api;
import org.ioteatime.meonghanyangserver.common.utils.LoginMember;
import org.ioteatime.meonghanyangserver.groupmember.dto.request.JoinGroupMemberRequest;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

@Tag(name = "Group Member Api", description = "Group Member 관련 API 목록입니다.")
public interface GroupMemberApi {

    @Operation(summary = "참여자가 그룹에 입장을 요청합니다.", description = "담당자: 임지인")
    public Api<Void> joinGroupAsMember(
            @LoginMember Long memberId, @RequestBody JoinGroupMemberRequest joinGroupMemberRequest);

    @Operation(summary = "방장이 그룹에서 참여자를 제외합니다.", description = "담당자: 최민석")
    public Api<?> deleteMasterGroupMember(
            @LoginMember Long memberId,
            @PathVariable Long groupId,
            @PathVariable Long groupMemberId);
}
