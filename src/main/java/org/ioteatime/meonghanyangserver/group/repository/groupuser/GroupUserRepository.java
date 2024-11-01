package org.ioteatime.meonghanyangserver.group.repository.groupuser;

import java.util.Optional;
import org.ioteatime.meonghanyangserver.group.domain.GroupEntity;
import org.ioteatime.meonghanyangserver.group.domain.GroupUserEntity;

public interface GroupUserRepository {
    GroupUserEntity createGroupUser(GroupUserEntity groupUserEntity);

    boolean existsGroupUser(Long userId);

    Optional<GroupUserEntity> findByUserId(Long userId);

    GroupEntity findGroupUser(Long userId);
}
