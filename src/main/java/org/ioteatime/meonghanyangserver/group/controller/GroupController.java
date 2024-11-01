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
import org.ioteatime.meonghanyangserver.group.service.GroupUserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/group")
public class GroupController implements GroupApi {
    private final GroupService groupService;
    private final GroupUserService groupUserService;

    @PostMapping
    public Api<CreateGroupResponse> createGroup(@LoginMember Long userId) {
        CreateGroupResponse createGroupResponse = groupService.createGroup(userId);
        return Api.success(GroupSuccessType.CREATE_GROUP, createGroupResponse);
    }

    @GetMapping("/viewer")
    public Api<GroupInfoResponse> getUserGroupInfo(@LoginMember Long userId) {
        GroupInfoResponse groupInfoResponse = groupUserService.getUserGroupInfo(userId);
        return Api.success(GroupSuccessType.GET_GROUP_ID, groupInfoResponse);
    }

    @GetMapping("/cctv")
    public Api<CctvInviteResponse> generateCctvInvite(@LoginMember Long userId) {
        CctvInviteResponse cctvInviteResponse = groupUserService.generateCctvInvite(userId);
        return Api.success(GroupSuccessType.GET_CHANNEL_INFO, cctvInviteResponse);
    }

    @GetMapping("/info-list")
    public Api<GroupTotalResponse> getGroupTotalData(@LoginMember Long userId) {
        GroupTotalResponse groupTotalResponse = groupService.getGroupTotalData(userId);
        return Api.success(GroupSuccessType.GET_GROUP_TOTAL_INFO, groupTotalResponse);
    }
}
