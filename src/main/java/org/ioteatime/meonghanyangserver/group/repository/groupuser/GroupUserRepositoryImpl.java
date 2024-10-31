package org.ioteatime.meonghanyangserver.group.repository.groupuser;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.ioteatime.meonghanyangserver.group.domain.GroupUserEntity;
import org.ioteatime.meonghanyangserver.user.domain.UserEntity;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class GroupUserRepositoryImpl implements GroupUserRepository {
    private final JpaGroupUserRepository jpaGroupUserRepository;

    @Override
    public GroupUserEntity createGroupUser(GroupUserEntity groupUserEntity) {
        return jpaGroupUserRepository.save(groupUserEntity);
    }

    @Override
    public boolean existsGroupUser(UserEntity userEntity) {
        boolean groupUser = jpaGroupUserRepository.existsByUser(userEntity);
        return groupUser;
    }

    @Override
    public Optional<GroupUserEntity> findByUserId(Long userId) {
        return jpaGroupUserRepository.findByUserId(userId);
    }
}
