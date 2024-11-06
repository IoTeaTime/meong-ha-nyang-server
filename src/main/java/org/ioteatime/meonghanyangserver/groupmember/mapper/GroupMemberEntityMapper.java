package org.ioteatime.meonghanyangserver.groupmember.mapper;

import org.ioteatime.meonghanyangserver.group.domain.GroupEntity;
import org.ioteatime.meonghanyangserver.groupmember.doamin.GroupMemberEntity;
import org.ioteatime.meonghanyangserver.groupmember.doamin.enums.GroupMemberRole;
import org.ioteatime.meonghanyangserver.member.domain.MemberEntity;

public class GroupMemberEntityMapper {

    // master mapper
    public static GroupMemberEntity from(
            GroupEntity groupEntity,
            MemberEntity memberEntity,
            GroupMemberRole groupMemberRole,
            String thingUd) {
        return GroupMemberEntity.builder()
                .id(groupEntity.getId())
                .member(memberEntity)
                .role(groupMemberRole)
                .group(groupEntity)
                .member(memberEntity)
                .thingId(thingUd)
                .build();
    }
}
