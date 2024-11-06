package org.ioteatime.meonghanyangserver.groupmember.repository;

import java.util.Optional;
import org.ioteatime.meonghanyangserver.group.domain.GroupEntity;
import org.ioteatime.meonghanyangserver.groupmember.doamin.GroupMemberEntity;

public interface GroupMemberRepository {
    GroupMemberEntity createGroupMember(GroupMemberEntity groupMemberEntity);

    boolean existsGroupMember(Long memberId);

    Optional<GroupMemberEntity> findByMemberId(Long memberId);

    GroupEntity findGroupMember(Long memberId);

    boolean isParcitipantUserId(Long userId);

    void deleteById(Long id);

    GroupMemberEntity save(GroupMemberEntity device);

    GroupMemberEntity findByGroupIdAndMemberIdAndRole(Long memberId, Long groupId);
}
