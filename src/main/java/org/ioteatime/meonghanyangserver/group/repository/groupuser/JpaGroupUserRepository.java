package org.ioteatime.meonghanyangserver.group.repository.groupuser;

import java.util.Optional;
import org.ioteatime.meonghanyangserver.group.domain.GroupUserEntity;
import org.ioteatime.meonghanyangserver.group.domain.GroupUserId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaGroupUserRepository extends JpaRepository<GroupUserEntity, GroupUserId> {
    boolean existsByUserId(Long userId);

    Optional<GroupUserEntity> findByUserId(Long userId);
}
