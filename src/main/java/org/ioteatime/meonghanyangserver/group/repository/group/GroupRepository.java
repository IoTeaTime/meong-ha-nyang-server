package org.ioteatime.meonghanyangserver.group.repository.group;

import org.ioteatime.meonghanyangserver.group.domain.GroupEntity;

public interface GroupRepository {
    GroupEntity createGroup(GroupEntity groupEntity);
}
