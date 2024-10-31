package org.ioteatime.meonghanyangserver.group.repository.group;

import org.ioteatime.meonghanyangserver.group.domain.GroupEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaGroupRepository extends JpaRepository<GroupEntity, Long> {}
