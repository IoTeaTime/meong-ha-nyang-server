package org.ioteatime.meonghanyangserver.group.repository.groupuser;

import org.ioteatime.meonghanyangserver.group.domain.GroupUserEntity;
import org.ioteatime.meonghanyangserver.user.domain.UserEntity;

import java.util.Optional;

public interface GroupUserRepository {
    GroupUserEntity createGroupUser(GroupUserEntity groupUserEntity);

    boolean existsGroupUser(UserEntity userEntity);

    Optional<GroupUserEntity> findGroupUser(UserEntity userEntity);
}
