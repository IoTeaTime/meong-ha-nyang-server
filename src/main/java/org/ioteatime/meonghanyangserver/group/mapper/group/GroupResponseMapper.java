package org.ioteatime.meonghanyangserver.group.mapper.group;

import org.ioteatime.meonghanyangserver.group.domain.GroupEntity;
import org.ioteatime.meonghanyangserver.group.dto.response.CreateGroupResponse;

public class GroupResponseMapper {
    public static CreateGroupResponse from(GroupEntity groupEntity) {
        return new CreateGroupResponse(
                groupEntity.getId(), groupEntity.getGroupName(), groupEntity.getCreatedAt());
    }
}
