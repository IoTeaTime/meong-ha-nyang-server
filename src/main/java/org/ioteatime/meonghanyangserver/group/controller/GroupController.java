package org.ioteatime.meonghanyangserver.group.controller;

import lombok.RequiredArgsConstructor;
import org.ioteatime.meonghanyangserver.cctv.dto.response.CctvInviteResponse;
import org.ioteatime.meonghanyangserver.common.api.Api;
import org.ioteatime.meonghanyangserver.common.type.GroupSuccessType;
import org.ioteatime.meonghanyangserver.common.utils.LoginMember;
import org.ioteatime.meonghanyangserver.group.dto.response.CreateGroupResponse;
import org.ioteatime.meonghanyangserver.group.dto.response.GroupInfoResponse;
import org.ioteatime.meonghanyangserver.group.dto.response.GroupTotalResponse;
import org.ioteatime.meonghanyangserver.group.service.GroupService;
import org.ioteatime.meonghanyangserver.groupmember.service.GroupMemberService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/group")
public class GroupController implements GroupApi {
    private final GroupService groupService;
    private final GroupMemberService groupMemberService;

    @PostMapping
    public Api<CreateGroupResponse> createGroup(@LoginMember Long memberId) {
        CreateGroupResponse createGroupResponse = groupService.createGroup(memberId);
        return Api.success(GroupSuccessType.CREATE_GROUP, createGroupResponse);
    }

    @GetMapping("/viewer")
    public Api<GroupInfoResponse> getUserGroupInfo(@LoginMember Long memberId) {
        GroupInfoResponse groupInfoResponse = groupMemberService.getUserGroupInfo(memberId);
        return Api.success(GroupSuccessType.GET_GROUP_ID, groupInfoResponse);
    }

    @GetMapping("/cctv")
    public Api<CctvInviteResponse> generateCctvInvite(@LoginMember Long memberId) {
        CctvInviteResponse cctvInviteResponse = groupMemberService.generateCctvInvite(memberId);
        return Api.success(GroupSuccessType.GET_CHANNEL_INFO, cctvInviteResponse);
    }

    @GetMapping("/info-list")
    public Api<GroupTotalResponse> getGroupTotalData(@LoginMember Long memberId) {
        GroupTotalResponse groupTotalResponse = groupService.getGroupTotalData(memberId);
        return Api.success(GroupSuccessType.GET_GROUP_TOTAL_INFO, groupTotalResponse);
    }
}
