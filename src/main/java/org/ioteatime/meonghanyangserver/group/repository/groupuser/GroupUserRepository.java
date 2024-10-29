package org.ioteatime.meonghanyangserver.group.repository.groupuser;

import org.ioteatime.meonghanyangserver.group.domain.GroupUserEntity;
import org.ioteatime.meonghanyangserver.user.domain.UserEntity;

public interface GroupUserRepository {
    GroupUserEntity createGroupUser(GroupUserEntity groupUserEntity);

    boolean findGroupUser(UserEntity userEntity);
}
