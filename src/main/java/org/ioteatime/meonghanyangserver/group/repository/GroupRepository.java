package org.ioteatime.meonghanyangserver.group.repository;

import org.ioteatime.meonghanyangserver.group.domain.GroupEntity;

public interface GroupRepository {
    GroupEntity createGroup(GroupEntity groupEntity);
}
