package org.ioteatime.meonghanyangserver.groupmember.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.ioteatime.meonghanyangserver.common.api.Api;
import org.ioteatime.meonghanyangserver.common.type.GroupSuccessType;
import org.ioteatime.meonghanyangserver.common.utils.LoginMember;
import org.ioteatime.meonghanyangserver.groupmember.dto.request.JoinGroupMemberRequest;
import org.ioteatime.meonghanyangserver.groupmember.dto.response.GroupMemberInfoListResponse;
import org.ioteatime.meonghanyangserver.groupmember.dto.response.GroupMemberResponse;
import org.ioteatime.meonghanyangserver.groupmember.service.GroupMemberService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/group")
public class GroupMemberController implements GroupMemberApi {
    private final GroupMemberService groupMemberService;

    @GetMapping
    public Api<GroupMemberResponse> getGroupMemberInfo(@LoginMember Long memberId) {
        GroupMemberResponse groupMemberResponse = groupMemberService.getGroupMemberInfo(memberId);
        return Api.success(GroupSuccessType.GET_GROUP_MEMBER_INFO, groupMemberResponse);
    }

    @PostMapping("/viewer")
    public Api<?> joinGroupAsMember(
            @LoginMember Long memberId,
            @Valid @RequestBody JoinGroupMemberRequest joinGroupMemberRequest) {
        groupMemberService.joinGroupMember(memberId, joinGroupMemberRequest);
        return Api.success(GroupSuccessType.JOIN_GROUP_MEMBER);
    }

    @DeleteMapping("/{groupId}/member/{groupMemberId}")
    public Api<?> deleteMasterGroupMember(
            @LoginMember Long memberId,
            @PathVariable("groupId") Long groupId,
            @PathVariable("groupMemberId") Long groupMemberId) {
        groupMemberService.deleteMasterGroupMember(memberId, groupId, groupMemberId);
        return Api.success(GroupSuccessType.DELETE_GROUP_MEMBER);
    }

    @DeleteMapping("/{groupId}/member")
    public Api<?> deleteGroupMember(
            @LoginMember Long memberId, @PathVariable("groupId") Long groupId) {
        groupMemberService.deleteGroupMember(memberId, groupId);
        return Api.success(GroupSuccessType.DELETE_GROUP_MEMBER);
    }

    @GetMapping("/{groupId}/member")
    public Api<GroupMemberInfoListResponse> getGroupMemberInfoList(
            @LoginMember Long memberId, @PathVariable("groupId") Long groupId) {
        GroupMemberInfoListResponse groupMemberInfoListResponse =
                groupMemberService.getGroupMemberInfoList(memberId, groupId);
        return Api.success(
                GroupSuccessType.GET_GROUP_MEMBER_INFO_LIST, groupMemberInfoListResponse);
    }
}
