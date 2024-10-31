package org.ioteatime.meonghanyangserver.group.controller;

import lombok.RequiredArgsConstructor;
import org.ioteatime.meonghanyangserver.cctv.dto.response.CctvInviteResponse;
import org.ioteatime.meonghanyangserver.common.api.Api;
import org.ioteatime.meonghanyangserver.common.type.GroupSuccessType;
import org.ioteatime.meonghanyangserver.common.utils.LoginMember;
import org.ioteatime.meonghanyangserver.group.dto.response.CreateGroupResponse;
import org.ioteatime.meonghanyangserver.group.dto.response.GroupInfoResponse;
import org.ioteatime.meonghanyangserver.group.service.GroupService;
import org.ioteatime.meonghanyangserver.group.service.GroupUserService;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/group")
public class GroupController implements GroupApi {
    private final GroupService groupService;
    private final GroupUserService groupUserService;

    @PostMapping
    public Api<CreateGroupResponse> createGroup(Authentication authentication) {
        CreateGroupResponse createGroupResponse = groupService.createGroup(authentication);
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
}
