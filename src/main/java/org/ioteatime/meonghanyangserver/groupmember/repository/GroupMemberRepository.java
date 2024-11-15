package org.ioteatime.meonghanyangserver.groupmember.repository;

import java.util.Optional;
import org.ioteatime.meonghanyangserver.group.domain.GroupEntity;
import org.ioteatime.meonghanyangserver.groupmember.doamin.GroupMemberEntity;

public interface GroupMemberRepository {
    GroupMemberEntity save(GroupMemberEntity groupMemberEntity);

    boolean existsGroupMember(Long memberId);

    Optional<GroupMemberEntity> findByMemberId(Long memberId);

    GroupEntity findGroupMember(Long memberId);

    void deleteById(Long id);

    boolean isMasterMember(Long memberId);

    Optional<GroupMemberEntity> findByGroupIdAndMemberIdAndRole(Long memberId, Long groupId);

    void deleteByGroupIdAndMemberId(Long groupId, Long memberId);

    Optional<GroupMemberEntity> findByGroupIdAndMemberId(Long groupId, Long memberId);

    void deleteByGroupId(Long groupId);
}
