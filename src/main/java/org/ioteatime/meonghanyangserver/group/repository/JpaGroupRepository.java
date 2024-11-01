package org.ioteatime.meonghanyangserver.group.repository;

import org.ioteatime.meonghanyangserver.group.domain.GroupEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaGroupRepository extends JpaRepository<GroupEntity, Long> {}