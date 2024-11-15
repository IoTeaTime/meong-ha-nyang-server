package org.ioteatime.meonghanyangserver.groupmember.controller;

import lombok.RequiredArgsConstructor;
import org.ioteatime.meonghanyangserver.common.api.Api;
import org.ioteatime.meonghanyangserver.common.type.GroupSuccessType;
import org.ioteatime.meonghanyangserver.common.utils.LoginMember;
import org.ioteatime.meonghanyangserver.groupmember.dto.request.JoinGroupMemberRequest;
import org.ioteatime.meonghanyangserver.groupmember.service.GroupMemberService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.ioteatime.meonghanyangserver.common.api.Api;
import org.ioteatime.meonghanyangserver.common.type.GroupSuccessType;
import org.ioteatime.meonghanyangserver.common.utils.LoginMember;
import org.ioteatime.meonghanyangserver.groupmember.service.GroupMemberService;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/group")
public class GroupMemberController implements GroupMemberApi {
    private final GroupMemberService groupMemberService;

    @DeleteMapping("/{groupId}/member/{groupMemberId}")
    public Api<?> deleteMasterGroupMember(
            @LoginMember Long memberId,
            @PathVariable Long groupId,
            @PathVariable Long groupMemberId) {
        groupMemberService.deleteMasterGroupMember(memberId, groupId, groupMemberId);
        return Api.success(GroupSuccessType.DELETE_GROUP_MEMBER);
        }
    @PostMapping("/viewer")
    public Api<Void> joinGroupAsMember(
            @LoginMember Long memberId,
            @RequestBody JoinGroupMemberRequest joinGroupMemberRequest) {
        groupMemberService.joinGroupMember(memberId, joinGroupMemberRequest);
        return Api.success(GroupSuccessType.JOIN_GROUP_MEMBER);
    }
}
