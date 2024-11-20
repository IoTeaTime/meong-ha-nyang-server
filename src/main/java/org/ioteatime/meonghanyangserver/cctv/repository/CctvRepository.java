package org.ioteatime.meonghanyangserver.cctv.repository;

import java.util.List;
import java.util.Optional;
import org.ioteatime.meonghanyangserver.cctv.domain.CctvEntity;

public interface CctvRepository {
    boolean existsByKvsChannelName(String kvsChannelName);

    boolean existsByThingId(String thingId);

    void deleteById(Long cctvId);

    CctvEntity save(CctvEntity cctv);

    Optional<CctvEntity> findById(Long cctvId);

    void deleteByCctvId(Long groupId);

    Optional<CctvEntity> findByCctvId(Long thingId);

    List<CctvEntity> findByGroupId(Long groupId);
}
