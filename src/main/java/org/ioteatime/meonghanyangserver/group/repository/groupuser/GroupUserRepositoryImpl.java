package org.ioteatime.meonghanyangserver.group.repository.groupuser;

import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.ioteatime.meonghanyangserver.group.domain.GroupEntity;
import org.ioteatime.meonghanyangserver.group.domain.GroupUserEntity;
import org.ioteatime.meonghanyangserver.group.domain.QGroupUserEntity;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class GroupUserRepositoryImpl implements GroupUserRepository {
    private final JpaGroupUserRepository jpaGroupUserRepository;

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public GroupUserEntity createGroupUser(GroupUserEntity groupUserEntity) {
        return jpaGroupUserRepository.save(groupUserEntity);
    }

    @Override
    public boolean existsGroupUser(Long userId) {
        boolean groupUser = jpaGroupUserRepository.existsByUserId(userId);
        return groupUser;
    }

    @Override
    public Optional<GroupUserEntity> findByUserId(Long userId) {
        return jpaGroupUserRepository.findByUserId(userId);
    }

    @Override
    public GroupEntity findGroupUser(Long userId) {
        QGroupUserEntity groupUserEntity = QGroupUserEntity.groupUserEntity;

        return jpaQueryFactory
                .select(groupUserEntity.group)
                .where(groupUserEntity.user.id.eq(userId))
                .from(groupUserEntity)
                .fetchOne();
    }
}
