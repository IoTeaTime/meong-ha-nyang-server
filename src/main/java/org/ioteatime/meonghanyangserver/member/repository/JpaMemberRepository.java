package org.ioteatime.meonghanyangserver.member.repository;

import java.util.Optional;
import org.ioteatime.meonghanyangserver.member.domain.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaMemberRepository extends JpaRepository<MemberEntity, Long> {
    Optional<MemberEntity> findByEmail(String email);
}
