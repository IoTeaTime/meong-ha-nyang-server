package org.ioteatime.meonghanyangserver.device.mapper;

import org.ioteatime.meonghanyangserver.device.doamin.DeviceEntity;
import org.ioteatime.meonghanyangserver.device.dto.response.DeviceInfoResponse;

public class DeviceResponseMapper {
    public static DeviceInfoResponse from(DeviceEntity deviceEntity) {
        return new DeviceInfoResponse(
                deviceEntity.getMember().getId(),
                deviceEntity.getMember().getNickname(),
                deviceEntity.getRole());
    }
}
