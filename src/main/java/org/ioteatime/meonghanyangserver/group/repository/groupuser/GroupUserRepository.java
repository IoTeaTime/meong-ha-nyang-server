package org.ioteatime.meonghanyangserver.group.repository.groupuser;

import java.util.Optional;
import org.ioteatime.meonghanyangserver.group.domain.GroupUserEntity;
import org.ioteatime.meonghanyangserver.user.domain.UserEntity;

public interface GroupUserRepository {
    GroupUserEntity createGroupUser(GroupUserEntity groupUserEntity);

    boolean existsGroupUser(UserEntity userEntity);

    Optional<GroupUserEntity> findByUserId(Long userId);

    Optional<GroupUserEntity> findGroupUser(UserEntity userEntity);
}
