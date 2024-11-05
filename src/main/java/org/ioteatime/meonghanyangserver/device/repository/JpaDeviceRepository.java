package org.ioteatime.meonghanyangserver.device.repository;

import java.util.Optional;
import org.ioteatime.meonghanyangserver.device.doamin.DeviceEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaDeviceRepository extends JpaRepository<DeviceEntity, Long> {
    boolean existsByMemberId(Long memberId);

    Optional<DeviceEntity> findByMemberId(Long memberId);
}
