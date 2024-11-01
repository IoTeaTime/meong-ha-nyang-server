package org.ioteatime.meonghanyangserver.cctv.repository;

import static org.ioteatime.meonghanyangserver.cctv.domain.QCctvEntity.cctvEntity;
import static org.ioteatime.meonghanyangserver.device.doamin.QDeviceEntity.deviceEntity;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.ioteatime.meonghanyangserver.cctv.domain.CctvEntity;
import org.ioteatime.meonghanyangserver.cctv.dto.db.CctvWithDeviceId;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class CctvRepositoryImpl implements CctvRepository {
    private final JPAQueryFactory jpaQueryFactory;
    private final JpaCctvRepository jpaCctvRepository;

    public boolean existsByKvsChannelName(String kvsChannelName) {
        return jpaCctvRepository.existsByKvsChannelName(kvsChannelName);
    }

    @Override
    public void deleteById(Long cctvId) {
        jpaCctvRepository.deleteById(cctvId);
    }

    @Override
    public Optional<CctvWithDeviceId> findByIdWithDeviceId(Long cctvId) {
        return Optional.ofNullable(
                jpaQueryFactory
                        .select(
                                Projections.constructor(
                                        CctvWithDeviceId.class,
                                        cctvEntity.id.as("cctvId"),
                                        cctvEntity.kvsChannelName.as("kvsChannelName"),
                                        cctvEntity.device.id.as("deviceId")))
                        .from(cctvEntity)
                        .join(cctvEntity.device, deviceEntity)
                        .where(cctvEntity.id.eq(cctvId))
                        .fetchOne());
    }

    @Override
    public CctvEntity save(CctvEntity cctv) {
        return jpaCctvRepository.save(cctv);
    }
}
