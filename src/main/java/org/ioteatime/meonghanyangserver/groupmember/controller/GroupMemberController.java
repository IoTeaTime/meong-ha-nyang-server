package org.ioteatime.meonghanyangserver.groupmember.controller;

import lombok.RequiredArgsConstructor;
import org.ioteatime.meonghanyangserver.common.api.Api;
import org.ioteatime.meonghanyangserver.common.type.GroupSuccessType;
import org.ioteatime.meonghanyangserver.common.utils.LoginMember;
import org.ioteatime.meonghanyangserver.groupmember.dto.request.JoinGroupMemberRequest;
import org.ioteatime.meonghanyangserver.groupmember.service.GroupMemberService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/group")
public class GroupMemberController {
    private final GroupMemberService groupMemberService;

    @PostMapping("/viewer")
    public Api<Void> joinGroupAsMemeber(
            @LoginMember Long memberId,
            @RequestBody JoinGroupMemberRequest joinGroupMemberRequest) {
        groupMemberService.joinGroupMember(memberId, joinGroupMemberRequest);
        return Api.success(GroupSuccessType.JOIN_GROUP_MEMBER);
    }
}
