package org.ioteatime.meonghanyangserver.user.repository;

import org.ioteatime.meonghanyangserver.user.domain.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository {

    Optional<UserEntity> findByEmail(String email);

}
