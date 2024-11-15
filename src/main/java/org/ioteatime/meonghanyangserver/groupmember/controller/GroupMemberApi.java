package org.ioteatime.meonghanyangserver.groupmember.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.ioteatime.meonghanyangserver.common.api.Api;
import org.ioteatime.meonghanyangserver.common.utils.LoginMember;
import org.ioteatime.meonghanyangserver.groupmember.dto.request.JoinGroupMemberRequest;
import org.springframework.web.bind.annotation.RequestBody;

@Tag(name = "Group-Member Api", description = "Group-Member 관련 API 목록입니다.")
public interface GroupMemberApi {

    @Operation(summary = "Viewer가 그룹에 입장을 요청합니다.", description = "담당자: 임지인")
    public Api<Void> joinGroupAsViewer(
            @LoginMember Long userId, @RequestBody JoinGroupMemberRequest joinGroupMemberRequest);
}
