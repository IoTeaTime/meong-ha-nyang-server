package org.ioteatime.meonghanyangserver.groupmember.mapper;

import org.ioteatime.meonghanyangserver.groupmember.doamin.GroupMemberEntity;
import org.ioteatime.meonghanyangserver.groupmember.dto.response.GroupMemberInfoResponse;

public class GroupMemberResponseMapper {
    public static GroupMemberInfoResponse from(GroupMemberEntity deviceEntity) {
        return new GroupMemberInfoResponse(
                deviceEntity.getMember().getId(),
                deviceEntity.getMember().getNickname(),
                deviceEntity.getRole());
    }
}
