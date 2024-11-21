package org.ioteatime.meonghanyangserver.cctv.repository;

import java.util.Optional;
import org.ioteatime.meonghanyangserver.cctv.domain.CctvEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaCctvRepository extends JpaRepository<CctvEntity, Long> {
    boolean existsByKvsChannelName(String kvsChannelName);

    boolean existsByThingId(String thingId);

    void deleteByGroupId(Long groupId);

    Optional<CctvEntity> findByThingId(String thingId);
}
