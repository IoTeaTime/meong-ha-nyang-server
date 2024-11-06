package org.ioteatime.meonghanyangserver.groupmember.repository;

import java.util.Optional;
import org.ioteatime.meonghanyangserver.group.domain.GroupEntity;
import org.ioteatime.meonghanyangserver.groupmember.doamin.GroupMemberEntity;

public interface GroupMemberRepository {
    GroupMemberEntity createGroupMember(GroupMemberEntity deviceEntity);

    boolean existsGroupMember(Long memberId);

    Optional<GroupMemberEntity> findByDeviceId(Long memberId);

    GroupEntity findDevice(Long memberId);

    boolean isParcitipantUserId(Long userId);

    void deleteById(Long id);

    GroupMemberEntity save(GroupMemberEntity device);
}
