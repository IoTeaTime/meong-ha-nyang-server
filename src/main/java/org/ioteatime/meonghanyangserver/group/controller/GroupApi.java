package org.ioteatime.meonghanyangserver.group.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.ioteatime.meonghanyangserver.common.api.Api;
import org.ioteatime.meonghanyangserver.group.dto.response.CreateGroupResponse;
import org.springframework.security.core.Authentication;

@Tag(name = "Group Api", description = "Group 관련 API 목록입니다.")
public interface GroupApi {
    @Operation(summary = "그룹을 생성합니다.")
    Api<CreateGroupResponse> createGroup(Authentication authentication);
}
