package org.ioteatime.meonghanyangserver.groupmember.service;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.ioteatime.meonghanyangserver.cctv.dto.response.CctvInviteResponse;
import org.ioteatime.meonghanyangserver.common.exception.NotFoundException;
import org.ioteatime.meonghanyangserver.common.type.GroupErrorType;
import org.ioteatime.meonghanyangserver.common.utils.KvsChannelNameGenerator;
import org.ioteatime.meonghanyangserver.group.domain.GroupEntity;
import org.ioteatime.meonghanyangserver.group.dto.response.GroupInfoResponse;
import org.ioteatime.meonghanyangserver.groupmember.doamin.GroupMemberEntity;
import org.ioteatime.meonghanyangserver.groupmember.doamin.enums.DeviceRole;
import org.ioteatime.meonghanyangserver.groupmember.mapper.GroupMemberEntityMapper;
import org.ioteatime.meonghanyangserver.groupmember.repository.GroupMemberRepository;
import org.ioteatime.meonghanyangserver.member.domain.MemberEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GroupMemberService {
    private final GroupMemberRepository deviceRepository;
    private final KvsChannelNameGenerator kvsChannelNameGenerator;

    // input user
    public void createDevice(
            GroupEntity groupEntity,
            MemberEntity memberEntity,
            DeviceRole deviceRole,
            String deviceUuid) {
        GroupMemberEntity deviceEntity =
                GroupMemberEntityMapper.from(groupEntity, memberEntity, deviceRole, deviceUuid);
        deviceRepository.createDevice(deviceEntity);
    }

    public boolean existsDevice(Long memberId) {
        return deviceRepository.existsDevice(memberId);
    }

    public GroupInfoResponse getUserGroupInfo(Long memberId) {
        GroupMemberEntity deviceEntity =
                deviceRepository
                        .findByDeviceId(memberId)
                        .orElseThrow(
                                () -> new NotFoundException(GroupErrorType.GROUP_USER_NOT_FOUND));

        return new GroupInfoResponse(deviceEntity.getGroup().getId());
    }

    public CctvInviteResponse generateCctvInvite(Long memberId) {
        GroupMemberEntity deviceEntity =
                deviceRepository
                        .findByDeviceId(memberId)
                        .orElseThrow(
                                () -> new NotFoundException(GroupErrorType.GROUP_USER_NOT_FOUND));
        String kvsChannelName = kvsChannelNameGenerator.generateUniqueKvsChannelName();

        return new CctvInviteResponse(deviceEntity.getGroup().getId(), kvsChannelName);
    }

    public GroupEntity getGroup(Long memberId) {
        return Optional.ofNullable(deviceRepository.findDevice(memberId))
                .orElseThrow(() -> new NotFoundException(GroupErrorType.GROUP_USER_NOT_FOUND));
    }
}
