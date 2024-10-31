package org.ioteatime.meonghanyangserver.group.service;

import lombok.RequiredArgsConstructor;
import org.ioteatime.meonghanyangserver.common.error.ErrorTypeCode;
import org.ioteatime.meonghanyangserver.common.exception.ApiExceptionImpl;
import org.ioteatime.meonghanyangserver.group.domain.GroupEntity;
import org.ioteatime.meonghanyangserver.group.domain.enums.GroupUserRole;
import org.ioteatime.meonghanyangserver.group.dto.response.CreateGroupResponse;
import org.ioteatime.meonghanyangserver.group.mapper.group.GroupEntityMapper;
import org.ioteatime.meonghanyangserver.group.mapper.group.GroupResponseMapper;
import org.ioteatime.meonghanyangserver.group.repository.group.GroupRepository;
import org.ioteatime.meonghanyangserver.user.domain.UserEntity;
import org.ioteatime.meonghanyangserver.user.dto.CustomUserDetail;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GroupService {
    private final GroupRepository groupRepository;
    private final GroupUserService groupUserService;

    // create group
    public CreateGroupResponse createGroup(Authentication authentication) {
        CustomUserDetail userDetails = (CustomUserDetail) authentication.getPrincipal();

        UserEntity userEntity = userDetails.getUserEntity();

        boolean groupUserEntity = groupUserService.existsGroupUser(userEntity);

        if (groupUserEntity) {
            throw new ApiExceptionImpl(ErrorTypeCode.BAD_REQUEST);
        }

        String roomName = userEntity.getNickname() + " 그룹";

        GroupEntity groupEntity = GroupEntityMapper.toEntity(roomName);

        GroupEntity newGroupEntity = groupRepository.createGroup(groupEntity);

        groupUserService.createGroupUser(newGroupEntity, userEntity, GroupUserRole.ROLE_MASTER);

        CreateGroupResponse createGroupResponse = GroupResponseMapper.toResponse(newGroupEntity);

        return createGroupResponse;
    }
}
