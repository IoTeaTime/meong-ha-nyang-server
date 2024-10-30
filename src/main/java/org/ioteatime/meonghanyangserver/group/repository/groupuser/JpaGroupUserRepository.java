package org.ioteatime.meonghanyangserver.group.repository.groupuser;

import java.util.Optional;
import org.ioteatime.meonghanyangserver.group.domain.GroupUserEntity;
import org.ioteatime.meonghanyangserver.group.domain.GroupUserId;
import org.ioteatime.meonghanyangserver.user.domain.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaGroupUserRepository extends JpaRepository<GroupUserEntity, GroupUserId> {
    boolean existsByUser(UserEntity userEntity);

    Optional<GroupUserEntity> findByUser(UserEntity userEntity);
}
