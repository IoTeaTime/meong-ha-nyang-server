package org.ioteatime.meonghanyangserver.group.repository;

import java.util.Optional;
import org.ioteatime.meonghanyangserver.group.domain.GroupEntity;

public interface GroupRepository {
    GroupEntity save(GroupEntity groupEntity);

    Optional<GroupEntity> findById(Long groupId);
}
