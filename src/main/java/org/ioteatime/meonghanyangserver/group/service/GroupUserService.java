package org.ioteatime.meonghanyangserver.group.service;

import lombok.RequiredArgsConstructor;
import org.ioteatime.meonghanyangserver.cctv.dto.response.CctvInviteResponse;
import org.ioteatime.meonghanyangserver.common.error.ErrorTypeCode;
import org.ioteatime.meonghanyangserver.common.exception.ApiExceptionImpl;
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
                                () ->
                                        new ApiExceptionImpl(
                                                ErrorTypeCode.BAD_REQUEST, "Group User not found"));

        return new GroupInfoResponse(groupUserEntity.getGroup().getId());
    }

    public CctvInviteResponse generateCctvInvite(Long userId) {
        GroupUserEntity groupUserEntity =
                groupUserRepository
                        .findByUserId(userId)
                        .orElseThrow(
                                () ->
                                        new ApiExceptionImpl(
                                                ErrorTypeCode.BAD_REQUEST, "Group User not found"));
        String kvsChannelName = kvsChannelNameGenerator.generateUniqueKvsChannelName();

        return new CctvInviteResponse(groupUserEntity.getGroup().getId(), kvsChannelName);
    }
}
