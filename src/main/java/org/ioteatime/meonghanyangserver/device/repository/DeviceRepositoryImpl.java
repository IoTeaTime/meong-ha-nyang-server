package org.ioteatime.meonghanyangserver.device.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;

import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.ioteatime.meonghanyangserver.cctv.domain.CctvEntity;
import org.ioteatime.meonghanyangserver.cctv.domain.QCctvEntity;
import org.ioteatime.meonghanyangserver.device.doamin.DeviceEntity;
import org.ioteatime.meonghanyangserver.device.doamin.QDeviceEntity;
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
        QDeviceEntity groupUserEntity = QDeviceEntity.deviceEntity;

        return jpaQueryFactory
                .select(groupUserEntity.group)
                .where(groupUserEntity.user.id.eq(userId))
                .from(groupUserEntity)
                .fetchOne();
    }

}
