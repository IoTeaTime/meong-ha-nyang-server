package org.ioteatime.meonghanyangserver.user.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.ioteatime.meonghanyangserver.device.doamin.QDeviceEntity;
import org.ioteatime.meonghanyangserver.user.domain.QUserEntity;
import org.ioteatime.meonghanyangserver.user.domain.UserEntity;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository {
    private final JpaUserRepository jpaUserRepository;
    private final JPAQueryFactory queryFactory;

    @Override
    public Optional<UserEntity> findById(Long userId) {
        return jpaUserRepository.findById(userId);
    }

    @Override
    public Optional<UserEntity> findByEmail(String email) {
        return jpaUserRepository.findByEmail(email);
    }

    @Override
    @Transactional
    public void deleteById(Long userId) {

        QDeviceEntity groupUser = QDeviceEntity.deviceEntity;
        QUserEntity user = QUserEntity.userEntity;

        queryFactory.delete(groupUser).where(groupUser.user.id.eq(userId)).execute();

        queryFactory.delete(user).where(user.id.eq(userId)).execute();
    }

    @Override
    public UserEntity save(UserEntity userEntity) {
        return jpaUserRepository.save(userEntity);
    }
}
