package org.ioteatime.meonghanyangserver.cctv.repository;

import java.util.List;
import java.util.Optional;
import org.ioteatime.meonghanyangserver.cctv.domain.CctvEntity;
import org.ioteatime.meonghanyangserver.cctv.dto.db.CctvWithGroupId;

public interface CctvRepository {
    boolean existsById(Long cctvId);

    boolean existsByKvsChannelName(String kvsChannelName);

    boolean existsByThingId(String thingId);

    void deleteById(Long cctvId);

    CctvEntity save(CctvEntity cctv);

    Optional<CctvEntity> findById(Long cctvId);

    void deleteByGroupId(Long groupId);

    Optional<CctvEntity> findByCctvId(Long cctvId);

    List<CctvEntity> findByGroupId(Long groupId);

    Optional<CctvWithGroupId> findCctvWithGroupIdByCctvId(Long cctvId);

    Optional<CctvEntity> findByThingId(String thingId);
}
