package org.ioteatime.meonghanyangserver.device.repository;

import java.util.List;
import java.util.Optional;

import org.ioteatime.meonghanyangserver.cctv.domain.CctvEntity;
import org.ioteatime.meonghanyangserver.device.doamin.DeviceEntity;
import org.ioteatime.meonghanyangserver.group.domain.GroupEntity;

public interface DeviceRepository {
    DeviceEntity createDevice(DeviceEntity deviceEntity);

    boolean existsDevice(Long userId);

    Optional<DeviceEntity> findByDeviceId(Long userId);

    GroupEntity findDevice(Long userId);
}
