package org.ioteatime.meonghanyangserver.group.service;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.ioteatime.meonghanyangserver.common.exception.BadRequestException;
import org.ioteatime.meonghanyangserver.common.exception.NotFoundException;
import org.ioteatime.meonghanyangserver.common.type.AuthErrorType;
import org.ioteatime.meonghanyangserver.common.type.GroupErrorType;
import org.ioteatime.meonghanyangserver.device.doamin.enums.DeviceRole;
import org.ioteatime.meonghanyangserver.device.repository.JpaDeviceRepository;
import org.ioteatime.meonghanyangserver.device.service.DeviceService;
import org.ioteatime.meonghanyangserver.group.domain.GroupEntity;
import org.ioteatime.meonghanyangserver.group.dto.request.CreateGroupRequest;
import org.ioteatime.meonghanyangserver.group.dto.response.CreateGroupResponse;
import org.ioteatime.meonghanyangserver.group.dto.response.GroupTotalResponse;
import org.ioteatime.meonghanyangserver.group.mapper.GroupEntityMapper;
import org.ioteatime.meonghanyangserver.group.mapper.GroupResponseMapper;
import org.ioteatime.meonghanyangserver.group.repository.GroupRepository;
import org.ioteatime.meonghanyangserver.member.domain.MemberEntity;
import org.ioteatime.meonghanyangserver.member.repository.MemberRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GroupService {
    private final GroupRepository groupRepository;
    private final DeviceService deviceService;
    private final MemberRepository memberRepository;
    private final JpaDeviceRepository groupUserRepository;

    // create group
    public CreateGroupResponse createGroup(Long memberId, CreateGroupRequest createGroupRequest) {

        boolean groupUserEntity = deviceService.existsDevice(memberId);

        if (groupUserEntity) {
            throw new BadRequestException(GroupErrorType.ALREADY_EXISTS);
        }

        MemberEntity memberEntity =
                memberRepository
                        .findById(memberId)
                        .orElseThrow(() -> new NotFoundException(AuthErrorType.NOT_FOUND));

        String roomName = memberEntity.getNickname() + " 그룹";

        GroupEntity groupEntity = GroupEntityMapper.toEntity(roomName);

        GroupEntity newGroupEntity = groupRepository.save(groupEntity);

        deviceService.createDevice(
                newGroupEntity,
                memberEntity,
                DeviceRole.ROLE_MASTER,
                createGroupRequest.deviceUuid());

        return GroupResponseMapper.from(newGroupEntity);
    }

    public GroupTotalResponse getGroupTotalData(Long memberId) {
        GroupEntity groupEntity =
                Optional.ofNullable(deviceService.getGroup(memberId))
                        .orElseThrow(
                                () -> new NotFoundException(GroupErrorType.GROUP_USER_NOT_FOUND));

        return GroupResponseMapper.fromGroupTotal(groupEntity);
    }
}
