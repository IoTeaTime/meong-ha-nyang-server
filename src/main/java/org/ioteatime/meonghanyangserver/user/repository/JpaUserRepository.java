package org.ioteatime.meonghanyangserver.user.repository;

import java.util.Optional;
import org.ioteatime.meonghanyangserver.user.domain.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaUserRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByEmail(String email);
}
