package org.ioteatime.meonghanyangserver.device.service;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.ioteatime.meonghanyangserver.cctv.dto.response.CctvInviteResponse;
import org.ioteatime.meonghanyangserver.common.exception.NotFoundException;
import org.ioteatime.meonghanyangserver.common.type.GroupErrorType;
import org.ioteatime.meonghanyangserver.common.utils.KvsChannelNameGenerator;
import org.ioteatime.meonghanyangserver.device.doamin.DeviceEntity;
import org.ioteatime.meonghanyangserver.device.doamin.enums.DeviceRole;
import org.ioteatime.meonghanyangserver.device.mapper.DeviceEntityMapper;
import org.ioteatime.meonghanyangserver.device.repository.DeviceRepository;
import org.ioteatime.meonghanyangserver.group.domain.GroupEntity;
import org.ioteatime.meonghanyangserver.group.dto.response.GroupInfoResponse;
import org.ioteatime.meonghanyangserver.member.domain.MemberEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeviceService {
    private final DeviceRepository deviceRepository;
    private final KvsChannelNameGenerator kvsChannelNameGenerator;

    // input user
    public void createDevice(
            GroupEntity groupEntity,
            MemberEntity memberEntity,
            DeviceRole deviceRole,
            String deviceUuid) {
        DeviceEntity deviceEntity =
                DeviceEntityMapper.from(groupEntity, memberEntity, deviceRole, deviceUuid);
        deviceRepository.createDevice(deviceEntity);
    }

    public boolean existsDevice(Long memberId) {
        return deviceRepository.existsDevice(memberId);
    }

    public GroupInfoResponse getUserGroupInfo(Long memberId) {
        DeviceEntity deviceEntity =
                deviceRepository
                        .findByDeviceId(memberId)
                        .orElseThrow(
                                () -> new NotFoundException(GroupErrorType.GROUP_USER_NOT_FOUND));

        return new GroupInfoResponse(deviceEntity.getGroup().getId());
    }

    public CctvInviteResponse generateCctvInvite(Long memberId) {
        DeviceEntity deviceEntity =
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
