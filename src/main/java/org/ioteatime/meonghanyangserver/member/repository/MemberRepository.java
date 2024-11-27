package org.ioteatime.meonghanyangserver.member.repository;

import java.util.Optional;
import org.ioteatime.meonghanyangserver.auth.dto.db.LoginWithMemberInfo;
import org.ioteatime.meonghanyangserver.member.domain.MemberEntity;

public interface MemberRepository {
    Optional<MemberEntity> findById(Long memberId);

    Optional<MemberEntity> findByEmail(String email);

    void deleteById(Long memberId);

    MemberEntity save(MemberEntity MemberEntity);

    Optional<MemberEntity> updateFcmTokenById(Long memberId, String token);

    Optional<LoginWithMemberInfo> findGroupMemberInfoByEmail(String email);
}
