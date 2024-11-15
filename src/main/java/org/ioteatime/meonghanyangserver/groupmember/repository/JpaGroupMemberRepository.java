package org.ioteatime.meonghanyangserver.groupmember.repository;

import java.util.Optional;
import org.ioteatime.meonghanyangserver.groupmember.doamin.GroupMemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaGroupMemberRepository extends JpaRepository<GroupMemberEntity, Long> {
    boolean existsByMemberId(Long memberId);

    Optional<GroupMemberEntity> findByMemberId(Long memberId);

    void deleteByGroupIdAndMemberId(Long groupId, Long memberId);

    void deleteByGroupId(Long groupId);
}
