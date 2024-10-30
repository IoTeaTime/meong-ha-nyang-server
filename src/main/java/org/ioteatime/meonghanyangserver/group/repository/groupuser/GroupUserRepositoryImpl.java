package org.ioteatime.meonghanyangserver.group.repository.groupuser;

import lombok.RequiredArgsConstructor;
import org.ioteatime.meonghanyangserver.group.domain.GroupUserEntity;
import org.ioteatime.meonghanyangserver.user.domain.UserEntity;
import org.springframework.stereotype.Repository;

import java.util.Optional;

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
    public Optional<GroupUserEntity> findGroupUser(UserEntity userEntity) {
        Optional<GroupUserEntity> groupUserEntity = jpaGroupUserRepository.findByUser(userEntity);
        return groupUserEntity;
    }
}
