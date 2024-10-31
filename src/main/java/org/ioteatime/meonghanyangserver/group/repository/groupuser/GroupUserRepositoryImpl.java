package org.ioteatime.meonghanyangserver.group.repository.groupuser;

import lombok.RequiredArgsConstructor;
import org.ioteatime.meonghanyangserver.common.error.ErrorTypeCode;
import org.ioteatime.meonghanyangserver.common.exception.ApiException;
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
    public GroupUserEntity findGroupUser(UserEntity userEntity) {
        GroupUserEntity groupUserEntity =
                jpaGroupUserRepository
                        .findByUser(userEntity)
                        .orElseThrow(() -> new ApiException(ErrorTypeCode.NULL_POINT));
        return groupUserEntity;
    }
}
