package org.ioteatime.meonghanyangserver.device.mapper;

import org.ioteatime.meonghanyangserver.device.doamin.DeviceEntity;
import org.ioteatime.meonghanyangserver.device.doamin.enums.DeviceRole;
import org.ioteatime.meonghanyangserver.group.domain.GroupEntity;
import org.ioteatime.meonghanyangserver.user.domain.UserEntity;

public class DeviceEntityMapper {

    // master mapper
    public static DeviceEntity from(
            GroupEntity groupEntity, UserEntity userEntity, DeviceRole deviceRole, String deviceUuid) {
        return DeviceEntity.builder()
                .id(groupEntity.getId())
                .user(userEntity)
                .role(deviceRole)
                .group(groupEntity)
                .user(userEntity)
                .deviceUuid(deviceUuid)
                .build();
    }
}
