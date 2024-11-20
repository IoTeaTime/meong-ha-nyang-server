package org.ioteatime.meonghanyangserver.cctv.repository;

import static org.ioteatime.meonghanyangserver.cctv.domain.QCctvEntity.cctvEntity;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.ioteatime.meonghanyangserver.cctv.domain.CctvEntity;
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
    public boolean existsByThingId(String thingId) {
        return jpaCctvRepository.existsByThingId(thingId);
    }

    @Override
    public void deleteById(Long cctvId) {
        jpaCctvRepository.deleteById(cctvId);
    }

    @Override
    public CctvEntity save(CctvEntity cctv) {
        return jpaCctvRepository.save(cctv);
    }

    @Override
    public Optional<CctvEntity> findById(Long cctvId) {
        return jpaCctvRepository.findById(cctvId);
    }

    @Override
    public void deleteByCctvId(Long groupId) {
        jpaCctvRepository.deleteByGroupId(groupId);
    }

    @Override
    public Optional<CctvEntity> findByThingId(String thingId) {
        CctvEntity result =
                jpaQueryFactory
                        .select(
                                Projections.constructor(
                                        CctvEntity.class,
                                        cctvEntity.id,
                                        cctvEntity.cctvNickname,
                                        cctvEntity.thingId,
                                        cctvEntity.kvsChannelName))
                        .from(cctvEntity)
                        .where(cctvEntity.thingId.eq(thingId))
                        .fetchOne();
        return Optional.ofNullable(result);
    }
}
