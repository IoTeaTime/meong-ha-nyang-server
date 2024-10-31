package org.ioteatime.meonghanyangserver.group.service;

import lombok.RequiredArgsConstructor;
import org.ioteatime.meonghanyangserver.cctv.dto.response.CctvInviteResponse;
import org.ioteatime.meonghanyangserver.common.exception.NotFoundException;
import org.ioteatime.meonghanyangserver.common.type.GroupErrorType;
import org.ioteatime.meonghanyangserver.common.utils.KvsChannelNameGenerator;
import org.ioteatime.meonghanyangserver.group.domain.GroupEntity;
import org.ioteatime.meonghanyangserver.group.domain.GroupUserEntity;
import org.ioteatime.meonghanyangserver.group.domain.enums.GroupUserRole;
import org.ioteatime.meonghanyangserver.group.dto.response.GroupInfoResponse;
import org.ioteatime.meonghanyangserver.group.mapper.groupuser.GroupUserEntityMapper;
import org.ioteatime.meonghanyangserver.group.repository.groupuser.GroupUserRepository;
import org.ioteatime.meonghanyangserver.user.domain.UserEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GroupUserService {
    private final GroupUserRepository groupUserRepository;
    private final KvsChannelNameGenerator kvsChannelNameGenerator;

    // input user
    public void createGroupUser(
            GroupEntity groupEntity, UserEntity userEntity, GroupUserRole groupUserRole) {
        GroupUserEntity groupUserEntity =
                GroupUserEntityMapper.toEntity(groupEntity, userEntity, groupUserRole);
        groupUserRepository.createGroupUser(groupUserEntity);
    }

    public boolean existsGroupUser(UserEntity userEntity) {
        return groupUserRepository.existsGroupUser(userEntity);
    }

    public GroupInfoResponse getUserGroupInfo(Long userId) {
        GroupUserEntity groupUserEntity =
                groupUserRepository
                        .findByUserId(userId)
                        .orElseThrow(
                                () -> new NotFoundException(GroupErrorType.GROUP_USER_NOT_FOUND));

        return new GroupInfoResponse(groupUserEntity.getGroup().getId());
    }

    public CctvInviteResponse generateCctvInvite(Long userId) {
        GroupUserEntity groupUserEntity =
                groupUserRepository
                        .findByUserId(userId)
                        .orElseThrow(
                                () -> new NotFoundException(GroupErrorType.GROUP_USER_NOT_FOUND));
        String kvsChannelName = kvsChannelNameGenerator.generateUniqueKvsChannelName();

        return new CctvInviteResponse(groupUserEntity.getGroup().getId(), kvsChannelName);
    }

    public GroupUserEntity getGroupUser(UserEntity userEntity) {
        GroupUserEntity groupUserEntity =
                groupUserRepository
                        .findGroupUser(userEntity)
                        .orElseThrow(
                                () -> new NotFoundException(GroupErrorType.GROUP_USER_NOT_FOUND));
        return groupUserEntity;
    }
}
