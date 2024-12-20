package org.ioteatime.meonghanyangserver.groupmember.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.ioteatime.meonghanyangserver.common.api.Api;
import org.ioteatime.meonghanyangserver.groupmember.dto.request.JoinGroupMemberRequest;
import org.ioteatime.meonghanyangserver.groupmember.dto.response.GroupMemberInfoListResponse;
import org.ioteatime.meonghanyangserver.groupmember.dto.response.GroupMemberResponse;

@Tag(name = "Group Member Api", description = "Group Member 관련 API 목록입니다.")
public interface GroupMemberApi {

    @Operation(summary = "참여자가 그룹과 역할을 조회합니다.", description = "담당자: 임지인")
    Api<GroupMemberResponse> getGroupMemberInfo(Long memberId);

    @Operation(summary = "참여자가 그룹에 입장을 요청합니다.", description = "담당자: 임지인")
    Api<?> joinGroupAsMember(Long memberId, JoinGroupMemberRequest joinGroupMemberRequest);

    @Operation(summary = "방장이 그룹에서 참여자를 제외합니다.", description = "담당자: 최민석")
    Api<?> deleteMasterGroupMember(Long memberId, Long groupId, Long groupMemberId);

    @Operation(summary = "그룹에서 참여자가 스스로 퇴장합니다.", description = "담당자: 최민석")
    Api<?> deleteGroupMember(Long memberId, Long groupId);

    @Operation(summary = "그룹 참여자들의 정보를 조회합니다.", description = "담당자: 최민석")
    Api<GroupMemberInfoListResponse> getGroupMemberInfoList(Long memberId, Long groupId);
}
