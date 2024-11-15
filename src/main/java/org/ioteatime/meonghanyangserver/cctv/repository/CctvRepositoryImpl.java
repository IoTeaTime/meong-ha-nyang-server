package org.ioteatime.meonghanyangserver.cctv.repository;

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
}
