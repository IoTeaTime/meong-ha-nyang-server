package org.ioteatime.meonghanyangserver.device.repository;

import java.util.Optional;
import org.ioteatime.meonghanyangserver.device.doamin.DeviceEntity;
import org.ioteatime.meonghanyangserver.group.domain.GroupEntity;

public interface DeviceRepository {
    DeviceEntity createDevice(DeviceEntity deviceEntity);

    boolean existsDevice(Long memberId);

    Optional<DeviceEntity> findByDeviceId(Long memberId);

    GroupEntity findDevice(Long memberId);

    boolean isParcitipantUserId(Long userId);

    void deleteById(Long id);

    DeviceEntity save(DeviceEntity device);
}
