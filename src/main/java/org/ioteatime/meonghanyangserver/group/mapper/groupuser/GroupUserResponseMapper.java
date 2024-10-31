package org.ioteatime.meonghanyangserver.group.mapper.groupuser;

import org.ioteatime.meonghanyangserver.group.domain.GroupUserEntity;
import org.ioteatime.meonghanyangserver.group.dto.response.GroupUserInfoResponse;

public class GroupUserResponseMapper {
    public static GroupUserInfoResponse toResponse(GroupUserEntity groupUserEntity) {
        return new GroupUserInfoResponse(groupUserEntity.getId().getUserId(), groupUserEntity.getUser().getNickname());
    }
}
