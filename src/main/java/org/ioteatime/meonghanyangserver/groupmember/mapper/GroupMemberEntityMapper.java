package org.ioteatime.meonghanyangserver.groupmember.mapper;

import org.ioteatime.meonghanyangserver.group.domain.GroupEntity;
import org.ioteatime.meonghanyangserver.groupmember.doamin.GroupMemberEntity;
import org.ioteatime.meonghanyangserver.groupmember.doamin.enums.DeviceRole;
import org.ioteatime.meonghanyangserver.member.domain.MemberEntity;

public class GroupMemberEntityMapper {

    // master mapper
    public static GroupMemberEntity from(
            GroupEntity groupEntity,
            MemberEntity memberEntity,
            DeviceRole deviceRole,
            String deviceUuid) {
        return GroupMemberEntity.builder()
                .id(groupEntity.getId())
                .member(memberEntity)
                .role(deviceRole)
                .group(groupEntity)
                .member(memberEntity)
                //                .deviceUuid(deviceUuid)
                .build();
    }
}
