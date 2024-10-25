package org.ioteatime.meonghanyangserver.user.repository;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.ioteatime.meonghanyangserver.user.domain.UserEntity;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository {
    private final JpaUserRepository jpaUserRepository;

    @Override
    public Optional<UserEntity> findById(Long userId) {
        return jpaUserRepository.findById(userId);
    }

    @Override
    public void deleteById(Long userId) {
        jpaUserRepository.deleteById(userId);
    }
}
