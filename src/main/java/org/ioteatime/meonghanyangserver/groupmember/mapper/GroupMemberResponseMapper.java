package org.ioteatime.meonghanyangserver.groupmember.mapper;

import org.ioteatime.meonghanyangserver.groupmember.doamin.GroupMemberEntity;
import org.ioteatime.meonghanyangserver.groupmember.dto.response.GroupMemberInfoResponse;

public class GroupMemberResponseMapper {
    public static GroupMemberInfoResponse from(GroupMemberEntity groupMemberEntity) {
        return new GroupMemberInfoResponse(
                groupMemberEntity.getMember().getId(),
                groupMemberEntity.getMember().getNickname(),
                groupMemberEntity.getThingId(),
                groupMemberEntity.getRole());
    }
}
