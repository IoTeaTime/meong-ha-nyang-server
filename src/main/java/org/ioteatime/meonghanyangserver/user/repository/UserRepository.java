package org.ioteatime.meonghanyangserver.user.repository;

import java.util.Optional;
import org.ioteatime.meonghanyangserver.user.domain.UserEntity;

public interface UserRepository {
    Optional<UserEntity> findById(Long userId);

    Optional<UserEntity> findByEmail(String email);
}
