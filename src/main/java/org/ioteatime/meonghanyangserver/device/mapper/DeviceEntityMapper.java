package org.ioteatime.meonghanyangserver.device.mapper;

import org.ioteatime.meonghanyangserver.device.doamin.DeviceEntity;
import org.ioteatime.meonghanyangserver.device.doamin.enums.DeviceRole;
import org.ioteatime.meonghanyangserver.group.domain.GroupEntity;
import org.ioteatime.meonghanyangserver.member.domain.MemberEntity;

public class DeviceEntityMapper {

    // master mapper
    public static DeviceEntity from(
            GroupEntity groupEntity,
            MemberEntity memberEntity,
            DeviceRole deviceRole,
            String deviceUuid) {
        return DeviceEntity.builder()
                .id(groupEntity.getId())
                .member(memberEntity)
                .role(deviceRole)
                .group(groupEntity)
                .member(memberEntity)
                .deviceUuid(deviceUuid)
                .build();
    }
}
