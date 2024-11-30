package org.ioteatime.meonghanyangserver.groupmember.repository;

import java.util.List;
import java.util.Optional;
import org.ioteatime.meonghanyangserver.group.domain.GroupEntity;
import org.ioteatime.meonghanyangserver.groupmember.doamin.GroupMemberEntity;
import org.ioteatime.meonghanyangserver.groupmember.doamin.enums.GroupMemberRole;

public interface GroupMemberRepository {
    GroupMemberEntity save(GroupMemberEntity groupMemberEntity);

    boolean existsGroupMember(Long memberId);

    Optional<GroupMemberEntity> findByMemberId(Long memberId);

    Optional<GroupEntity> findGropFromGroupMember(Long memberId);

    void deleteById(Long id);

    Optional<GroupMemberEntity> findByMemberIdAndGroupId(Long memberId, Long groupId);

    boolean isMasterMember(Long memberId);

    Optional<GroupMemberEntity> findByGroupIdAndMemberIdAndRole(Long memberId, Long groupId);

    void deleteByGroupIdAndMemberId(Long groupId, Long memberId);

    Optional<GroupMemberEntity> findByGroupIdAndMemberId(Long groupId, Long memberId);

    void deleteByGroupId(Long groupId);

    boolean existsByMemberIdAndGroupId(Long memberId, Long groupId);

    boolean existsByMemberIdAndGroupIdAndRole(
            Long memberId, Long groupId, GroupMemberRole groupMemberRole);

    List<GroupMemberEntity> findByGroupIdAndRole(Long groupId, GroupMemberRole groupMemberRole);

    void deleteAllByGroupId(Long groupId);
}
