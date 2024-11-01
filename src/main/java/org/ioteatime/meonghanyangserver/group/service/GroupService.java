package org.ioteatime.meonghanyangserver.group.service;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.ioteatime.meonghanyangserver.common.exception.BadRequestException;
import org.ioteatime.meonghanyangserver.common.exception.NotFoundException;
import org.ioteatime.meonghanyangserver.common.type.AuthErrorType;
import org.ioteatime.meonghanyangserver.common.type.GroupErrorType;
import org.ioteatime.meonghanyangserver.group.domain.GroupEntity;
import org.ioteatime.meonghanyangserver.group.domain.enums.GroupUserRole;
import org.ioteatime.meonghanyangserver.group.dto.response.CreateGroupResponse;
import org.ioteatime.meonghanyangserver.group.dto.response.GroupTotalResponse;
import org.ioteatime.meonghanyangserver.group.mapper.group.GroupEntityMapper;
import org.ioteatime.meonghanyangserver.group.mapper.group.GroupResponseMapper;
import org.ioteatime.meonghanyangserver.group.repository.group.GroupRepository;
import org.ioteatime.meonghanyangserver.group.repository.groupuser.JpaGroupUserRepository;
import org.ioteatime.meonghanyangserver.user.domain.UserEntity;
import org.ioteatime.meonghanyangserver.user.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GroupService {
    private final GroupRepository groupRepository;
    private final GroupUserService groupUserService;
    private final UserRepository userRepository;
    private final JpaGroupUserRepository groupUserRepository;

    // create group
    public CreateGroupResponse createGroup(Long userId) {

        boolean groupUserEntity = groupUserService.existsGroupUser(userId);

        if (groupUserEntity) {
            throw new BadRequestException(GroupErrorType.ALREADY_EXISTS);
        }

        UserEntity userEntity =
                userRepository
                        .findById(userId)
                        .orElseThrow(() -> new NotFoundException(AuthErrorType.NOT_FOUND));

        String roomName = userEntity.getNickname() + " 그룹";

        GroupEntity groupEntity = GroupEntityMapper.toEntity(roomName);

        GroupEntity newGroupEntity = groupRepository.createGroup(groupEntity);

        groupUserService.createGroupUser(newGroupEntity, userEntity, GroupUserRole.ROLE_MASTER);

        return GroupResponseMapper.from(newGroupEntity);
    }

    public GroupTotalResponse getGroupTotalData(Long userId) {
        GroupEntity groupEntity =
                Optional.ofNullable(groupUserService.getGroup(userId))
                        .orElseThrow(
                                () -> new NotFoundException(GroupErrorType.GROUP_USER_NOT_FOUND));

        return GroupResponseMapper.fromGroupTotal(groupEntity);
    }
}
