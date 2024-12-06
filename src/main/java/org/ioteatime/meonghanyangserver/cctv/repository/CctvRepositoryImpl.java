package org.ioteatime.meonghanyangserver.cctv.repository;

import static org.ioteatime.meonghanyangserver.cctv.domain.QCctvEntity.cctvEntity;
import static org.ioteatime.meonghanyangserver.group.domain.QGroupEntity.groupEntity;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.ioteatime.meonghanyangserver.cctv.domain.CctvEntity;
import org.ioteatime.meonghanyangserver.cctv.dto.db.CctvWithGroupId;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class CctvRepositoryImpl implements CctvRepository {
    private final JPAQueryFactory jpaQueryFactory;
    private final JpaCctvRepository jpaCctvRepository;

    public boolean existsById(Long cctvId) {
        return jpaCctvRepository.existsById(cctvId);
    }

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
    public void deleteByGroupId(Long groupId) {
        jpaCctvRepository.deleteByGroupId(groupId);
    }

    @Override
    public Optional<CctvEntity> findByCctvId(Long cctvId) {
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
                        .where(cctvEntity.id.eq(cctvId))
                        .fetchOne();
        return Optional.ofNullable(result);
    }

    @Override
    public List<CctvEntity> findByGroupId(Long groupId) {
        List<CctvEntity> result =
                jpaQueryFactory
                        .select(
                                Projections.constructor(
                                        CctvEntity.class,
                                        cctvEntity.id,
                                        cctvEntity.cctvNickname,
                                        cctvEntity.thingId,
                                        cctvEntity.kvsChannelName))
                        .from(cctvEntity)
                        .where(cctvEntity.group.id.eq(groupId))
                        .fetch();

        return result;
    }

    @Override
    public Optional<CctvEntity> findByThingId(String thingId) {
        return Optional.ofNullable(
                jpaQueryFactory
                        .selectFrom(cctvEntity)
                        .join(cctvEntity.group, groupEntity)
                        .fetchJoin()
                        .where(cctvEntity.thingId.eq(thingId))
                        .fetchOne());
    }

    @Override
    public Optional<CctvWithGroupId> findCctvWithGroupIdByCctvId(Long cctvId) {
        CctvWithGroupId result =
                jpaQueryFactory
                        .select(
                                Projections.constructor(
                                        CctvWithGroupId.class,
                                        cctvEntity.group.id,
                                        cctvEntity.id,
                                        cctvEntity.cctvNickname,
                                        cctvEntity.thingId,
                                        cctvEntity.kvsChannelName))
                        .from(cctvEntity)
                        .where(cctvEntity.id.eq(cctvId))
                        .fetchOne();
        return Optional.ofNullable(result);
    }
}
