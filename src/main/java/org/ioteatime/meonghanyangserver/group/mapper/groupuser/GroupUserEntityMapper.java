package org.ioteatime.meonghanyangserver.group.mapper.groupuser;

import org.ioteatime.meonghanyangserver.group.domain.GroupEntity;
import org.ioteatime.meonghanyangserver.group.domain.GroupUserEntity;
import org.ioteatime.meonghanyangserver.group.domain.GroupUserId;
import org.ioteatime.meonghanyangserver.group.domain.enums.GroupUserRole;
import org.ioteatime.meonghanyangserver.user.domain.UserEntity;

public class GroupUserEntityMapper {

    // master mapper
    public static GroupUserEntity toEntity(
            GroupEntity groupEntity, UserEntity userEntity, GroupUserRole groupUserRole) {
        return GroupUserEntity.builder()
                .id(new GroupUserId(groupEntity.getId(), userEntity.getId()))
                .user(userEntity)
                .role(groupUserRole)
                .group(groupEntity)
                .user(userEntity)
                .build();
    }
}
