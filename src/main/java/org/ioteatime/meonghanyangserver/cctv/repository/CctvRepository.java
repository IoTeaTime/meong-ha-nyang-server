package org.ioteatime.meonghanyangserver.cctv.repository;

import java.util.Optional;
import org.ioteatime.meonghanyangserver.cctv.domain.CctvEntity;
import org.ioteatime.meonghanyangserver.cctv.dto.db.CctvWithDeviceId;

public interface CctvRepository {
    boolean existsByKvsChannelName(String kvsChannelName);

    void deleteById(Long cctvId);

    Optional<CctvWithDeviceId> findByIdWithDeviceId(Long cctvId);

    CctvEntity save(CctvEntity cctv);
}
