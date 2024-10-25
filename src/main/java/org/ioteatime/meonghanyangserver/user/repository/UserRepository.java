package org.ioteatime.meonghanyangserver.user.repository;

import java.util.Optional;
import org.ioteatime.meonghanyangserver.user.domain.UserEntity;

public interface UserRepository {
    // email 중복 여부를 검사하는 쿼리 메서드
    boolean existsByEmail(String email);

    Optional<UserEntity> findByEmail(String email);

    UserEntity save(UserEntity UserEntity);
}
