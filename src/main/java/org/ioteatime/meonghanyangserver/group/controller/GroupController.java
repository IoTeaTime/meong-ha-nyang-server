package org.ioteatime.meonghanyangserver.group.controller;

import lombok.RequiredArgsConstructor;
import org.ioteatime.meonghanyangserver.common.api.Api;
import org.ioteatime.meonghanyangserver.common.type.GroupSuccessType;
import org.ioteatime.meonghanyangserver.group.dto.response.CreateGroupResponse;
import org.ioteatime.meonghanyangserver.group.service.GroupService;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/group")
public class GroupController implements GroupApi {
    private final GroupService groupService;

    @PostMapping
    public Api<CreateGroupResponse> createGroup(Authentication authentication) {
        CreateGroupResponse createGroupResponse = groupService.createGroup(authentication);
        return Api.success(GroupSuccessType.CREATE_GROUP, createGroupResponse);
    }
}
