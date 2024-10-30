package org.ioteatime.meonghanyangserver.group.service;

import lombok.RequiredArgsConstructor;
import org.ioteatime.meonghanyangserver.group.domain.GroupEntity;
import org.ioteatime.meonghanyangserver.group.domain.GroupUserEntity;
import org.ioteatime.meonghanyangserver.group.domain.enums.GroupUserRole;
import org.ioteatime.meonghanyangserver.group.mapper.groupuser.GroupUserEntityMapper;
import org.ioteatime.meonghanyangserver.group.repository.groupuser.GroupUserRepository;
import org.ioteatime.meonghanyangserver.user.domain.UserEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GroupUserService {
    private final GroupUserRepository groupUserRepository;

    // input user
    public void createGroupUser(
            GroupEntity groupEntity, UserEntity userEntity, GroupUserRole groupUserRole) {
        GroupUserEntity groupUserEntity =
                GroupUserEntityMapper.toEntity(groupEntity, userEntity, groupUserRole);
        groupUserRepository.createGroupUser(groupUserEntity);
    }

    public boolean findGroupUser(UserEntity userEntity) {
        boolean groupUser = groupUserRepository.findGroupUser(userEntity);
        return groupUser;
    }
}
