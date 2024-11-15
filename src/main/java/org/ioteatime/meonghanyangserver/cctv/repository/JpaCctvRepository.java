package org.ioteatime.meonghanyangserver.cctv.repository;

import org.ioteatime.meonghanyangserver.cctv.domain.CctvEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaCctvRepository extends JpaRepository<CctvEntity, Long> {
    boolean existsByKvsChannelName(String kvsChannelName);

    void deleteByGroupId(Long groupId);
}
