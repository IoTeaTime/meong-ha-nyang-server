package org.ioteatime.meonghanyangserver.group.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.ioteatime.meonghanyangserver.cctv.dto.response.CctvInviteResponse;
import org.ioteatime.meonghanyangserver.common.api.Api;
import org.ioteatime.meonghanyangserver.group.dto.request.CreateGroupRequest;
import org.ioteatime.meonghanyangserver.group.dto.response.CreateGroupResponse;
import org.ioteatime.meonghanyangserver.group.dto.response.GroupResponse;
import org.ioteatime.meonghanyangserver.group.dto.response.GroupTotalResponse;

@Tag(name = "Group Api", description = "Group 관련 API 목록입니다.")
public interface GroupApi {
    @Operation(summary = "그룹을 생성합니다.", description = "담당자: 최민석")
    Api<CreateGroupResponse> createGroup(Long memberId, CreateGroupRequest createGroupRequest);

    @Operation(summary = "그룹 초대를 위해 그룹 id를 조회합니다", description = "담당자: 임지인")
    Api<GroupResponse> getGroupInfo(Long memberId);

    @Operation(summary = "그룹 id와 신호 채널 이름을 전달합니다.", description = "담당자: 임지인")
    Api<CctvInviteResponse> generateCctvInvite(Long memberId);

    @Operation(summary = "그룹 통합 정보를 조회합니다.", description = "담당자: 최민석")
    Api<GroupTotalResponse> getGroupTotalData(Long memberId);
}
