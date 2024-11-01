package org.ioteatime.meonghanyangserver.device.repository;

import static org.ioteatime.meonghanyangserver.device.doamin.QDeviceEntity.deviceEntity;

import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.ioteatime.meonghanyangserver.device.doamin.DeviceEntity;
import org.ioteatime.meonghanyangserver.device.doamin.enums.DeviceRole;
import org.ioteatime.meonghanyangserver.group.domain.GroupEntity;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class DeviceRepositoryImpl implements DeviceRepository {
    private final JpaDeviceRepository jpaDeviceRepository;

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public DeviceEntity createDevice(DeviceEntity deviceEntity) {
        return jpaDeviceRepository.save(deviceEntity);
    }

    @Override
    public boolean existsDevice(Long userId) {
        return jpaDeviceRepository.existsByUserId(userId);
    }

    @Override
    public Optional<DeviceEntity> findByDeviceId(Long userId) {
        return jpaDeviceRepository.findByUserId(userId);
    }

    @Override
    public GroupEntity findDevice(Long userId) {
        return jpaQueryFactory
                .select(deviceEntity.group)
                .from(deviceEntity)
                .where(deviceEntity.user.id.eq(userId))
                .fetchOne();
    }

    @Override
    public boolean isMasterUserId(Long userId) {
        return !jpaQueryFactory
                .select(deviceEntity.role)
                .from(deviceEntity)
                .where(
                        deviceEntity
                                .user
                                .id
                                .eq(userId)
                                .and(deviceEntity.role.eq(DeviceRole.ROLE_MASTER)))
                .fetch()
                .isEmpty();
    }

    @Override
    public void deleteById(Long id) {
        jpaDeviceRepository.deleteById(id);
    }

    @Override
    public DeviceEntity save(DeviceEntity device) {
        return jpaDeviceRepository.save(device);
    }
}
